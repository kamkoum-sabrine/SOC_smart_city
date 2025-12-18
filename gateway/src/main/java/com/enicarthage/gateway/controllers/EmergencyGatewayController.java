package com.enicarthage.gateway.controllers;

import com.enicarthage.emergency.grpc.AlertResponse;
import com.enicarthage.emergency.grpc.DispatchResponse;
import com.enicarthage.gateway.clients.EmergencyClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emergency") // Ensure this matches Frontend URL
public class EmergencyGatewayController {

    private final EmergencyClient client;

    public EmergencyGatewayController(EmergencyClient client) {
        this.client = client;
    }

    @PostMapping("/alerts")
    public Map<String, Object> createAlert(@RequestBody Map<String, Object> request) {
        // Pass Strings directly to the client. The client handles Enum conversion.
        AlertResponse response = client.createAlert(
                (String) request.get("type"),
                (String) request.get("priority"), // Pass as String
                (String) request.get("description"),
                Double.parseDouble(request.get("latitude").toString()),
                Double.parseDouble(request.get("longitude").toString()),
                (String) request.get("zone"),
                (String) request.get("address"),
                (String) request.get("reporterName"),
                (String) request.get("reporterPhone"));

        return convertAlertResponseToMap(response);
    }

    @GetMapping("/alerts/active")
    public List<Map<String, Object>> getActiveAlerts() {
        List<AlertResponse> responses = client.getActiveAlerts();
        List<Map<String, Object>> jsonList = new ArrayList<>();
        for (AlertResponse res : responses) {
            jsonList.add(convertAlertResponseToMap(res));
        }
        return jsonList;
    }

    @PostMapping("/dispatch")
    public Map<String, Object> dispatchTeam(@RequestBody Map<String, Object> request) {
        String alertId = (String) request.get("alertId");
        String teamId = (String) request.get("teamId");
        int eta = Integer.parseInt(request.get("eta").toString());

        DispatchResponse response = client.dispatchTeam(alertId, teamId, eta);

        Map<String, Object> map = new HashMap<>();
        map.put("success", response.getSuccess());
        map.put("teamId", response.getTeamId());
        map.put("message", response.getMessage());
        map.put("estimatedArrivalTime", response.getEstimatedArrivalTime());
        return map;
    }

    private Map<String, Object> convertAlertResponseToMap(AlertResponse res) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", res.getAlertId());
        map.put("type", res.getType().name());
        map.put("priority", res.getPriority().name());
        map.put("description", res.getDescription());

        Map<String, Object> location = new HashMap<>();
        if (res.hasLocation()) {
            location.put("latitude", res.getLocation().getLatitude());
            location.put("longitude", res.getLocation().getLongitude());
            location.put("zone", res.getLocation().getZone());
            location.put("address", res.getLocation().getAddress());
        }
        map.put("location", location);
        map.put("reporterName", res.getReporterName());
        map.put("status", res.getStatus().name());
        return map;
    }
}
