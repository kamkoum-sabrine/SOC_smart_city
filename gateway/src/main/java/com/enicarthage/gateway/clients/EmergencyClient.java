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

    private final EmergencyServiceGrpc.EmergencyServiceBlockingStub stub;

    public EmergencyClient() {
        // Connect to the Docker service "emergency-grpc" on port 9091
        ManagedChannel channel = ManagedChannelBuilder.forAddress("emergency-grpc", 9091)
                .usePlaintext()
                .build();

        this.stub = EmergencyServiceGrpc.newBlockingStub(channel);
    }

    public AlertResponse createAlert(String type, String priority, String description,
            double lat, double lon, String zone, String address,
            String name, String phone) {

        // Build the Location object
        Location loc = Location.newBuilder()
                .setLatitude(lat)
                .setLongitude(lon)
                .setZone(zone)
                .setAddress(address)
                .build();

        // Build the Request using names from your .proto file
        AlertRequest request = AlertRequest.newBuilder()
                .setType(EmergencyType.valueOf(type)) // Matches Proto: EmergencyType
                .setPriority(Priority.valueOf(priority)) // Matches Proto: Priority
                .setDescription(description)
                .setLocation(loc)
                .setReporterName(name)
                .setReporterPhone(phone)
                .build();

        return stub.createAlert(request);
    }

    public List<AlertResponse> getActiveAlerts() {
        // Use the custom EmptyRequest from your proto
        EmptyRequest request = EmptyRequest.newBuilder().build();

        // The Proto defines this as 'rpc StreamActiveAlerts', so we use
        // streamActiveAlerts
        Iterator<AlertResponse> responseIterator = stub.streamActiveAlerts(request);

        // Convert the Iterator to a Java List
        List<AlertResponse> alertsList = new ArrayList<>();
        responseIterator.forEachRemaining(alertsList::add);

        return alertsList;
    }

    public DispatchResponse dispatchTeam(String alertId, String teamId, int eta) {
        DispatchRequest request = DispatchRequest.newBuilder()
                .setAlertId(alertId)
                .setTeamId(teamId)
                .setEstimatedArrivalTime(eta)
                .build();

        return stub.dispatchTeam(request);
    }
}
