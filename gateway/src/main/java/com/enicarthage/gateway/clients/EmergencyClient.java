package com.enicarthage.gateway.clients;

import com.enicarthage.emergency.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmergencyClient {

    private final ManagedChannel channel;
    private final EmergencyServiceGrpc.EmergencyServiceBlockingStub stub;

    public EmergencyClient() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.stub = EmergencyServiceGrpc.newBlockingStub(channel);
    }

    public AlertResponse createAlert(String type, Priority priority, String description,
                                     double lat, double lon, String zone, String address,
                                     String reporterName, String reporterPhone) {

        Location location = Location.newBuilder()
                .setLatitude(lat)
                .setLongitude(lon)
                .setZone(zone)
                .setAddress(address)
                .build();

        AlertRequest request = AlertRequest.newBuilder()
                .setType(EmergencyType.valueOf(type))
                .setPriority(priority)
                .setDescription(description)
                .setLocation(location)
                .setReporterName(reporterName)
                .setReporterPhone(reporterPhone)
                .build();

        return stub.createAlert(request);
    }

    public DispatchResponse dispatchTeam(String alertId, String teamId, int eta) {
        DispatchRequest request = DispatchRequest.newBuilder()
                .setAlertId(alertId)
                .setTeamId(teamId)
                .setEstimatedArrivalTime(eta)
                .build();
        return stub.dispatchTeam(request);
    }

    public AlertResponse getAlertStatus(String alertId) {
        AlertStatusRequest request = AlertStatusRequest.newBuilder()
                .setAlertId(alertId)
                .build();
        return stub.getAlertStatus(request);
    }

    public List<AlertResponse> getActiveAlerts() {
        EmptyRequest request = EmptyRequest.newBuilder().build();
        Iterator<AlertResponse> iterator = stub.streamActiveAlerts(request);

        List<AlertResponse> alerts = new ArrayList<>();
        iterator.forEachRemaining(alerts::add);

        return alerts;
    }

}
