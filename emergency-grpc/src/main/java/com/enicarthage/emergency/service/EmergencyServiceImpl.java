package com.enicarthage.emergency.service;

import com.enicarthage.emergency.grpc.*;
import com.enicarthage.emergency.model.EmergencyAlert;
import com.enicarthage.emergency.repository.EmergencyRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyServiceImpl extends EmergencyServiceGrpc.EmergencyServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(EmergencyServiceImpl.class);

    private final EmergencyManager emergencyManager;
    private final EmergencyRepository repository;

    public EmergencyServiceImpl(EmergencyManager emergencyManager, EmergencyRepository repository) {
        this.emergencyManager = emergencyManager;
        this.repository = repository;
    }

    @Override
    public void createAlert(AlertRequest request, StreamObserver<AlertResponse> responseObserver) {
        try {
            logger.info("Réception d'une nouvelle alerte: {}", request.getType());

            EmergencyAlert alert = new EmergencyAlert();
            alert.setType(request.getType());
            alert.setPriority(request.getPriority());
            alert.setDescription(request.getDescription());
            alert.setLatitude(request.getLocation().getLatitude());
            alert.setLongitude(request.getLocation().getLongitude());
            alert.setAddress(request.getLocation().getAddress());
            alert.setZone(request.getLocation().getZone());
            alert.setReporterName(request.getReporterName());
            alert.setReporterPhone(request.getReporterPhone());

            EmergencyAlert saved = emergencyManager.createAlert(alert);

            AlertResponse response = buildAlertResponse(saved);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Erreur lors de la création de l'alerte", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getAlertStatus(AlertStatusRequest request, StreamObserver<AlertResponse> responseObserver) {
        try {
            EmergencyAlert alert = repository.findById(request.getAlertId())
                    .orElseThrow(() -> new IllegalArgumentException("Alerte non trouvée"));

            AlertResponse response = buildAlertResponse(alert);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du statut", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateAlertStatus(UpdateStatusRequest request, StreamObserver<AlertResponse> responseObserver) {
        try {
            EmergencyAlert alert = emergencyManager.updateStatus(
                    request.getAlertId(),
                    request.getNewStatus(),
                    request.getNotes()
            );

            AlertResponse response = buildAlertResponse(alert);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du statut", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void streamActiveAlerts(EmptyRequest request, StreamObserver<AlertResponse> responseObserver) {
        try {
            List<EmergencyAlert> activeAlerts = repository.findAllActive();

            logger.info("📡 Streaming de {} alertes actives", activeAlerts.size());

            for (EmergencyAlert alert : activeAlerts) {
                AlertResponse response = buildAlertResponse(alert);
                responseObserver.onNext(response);
                Thread.sleep(100);
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Erreur lors du streaming des alertes", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void dispatchTeam(DispatchRequest request, StreamObserver<DispatchResponse> responseObserver) {
        try {
            emergencyManager.dispatchTeam(
                    request.getAlertId(),
                    request.getTeamId(),
                    request.getEstimatedArrivalTime()
            );

            DispatchResponse response = DispatchResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Équipe dispatchée avec succès")
                    .setTeamId(request.getTeamId())
                    .setEstimatedArrivalTime(request.getEstimatedArrivalTime())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("Erreur lors du dispatch de l'équipe", e);

            DispatchResponse errorResponse = DispatchResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Erreur: " + e.getMessage())
                    .build();

            responseObserver.onNext(errorResponse);
            responseObserver.onCompleted();
        }
    }

    private AlertResponse buildAlertResponse(EmergencyAlert alert) {
        Location location = Location.newBuilder()
                .setLatitude(alert.getLatitude())
                .setLongitude(alert.getLongitude())
                .setAddress(alert.getAddress())
                .setZone(alert.getZone())
                .build();

        return AlertResponse.newBuilder()
                .setAlertId(alert.getAlertId())
                .setType(alert.getType())
                .setPriority(alert.getPriority())
                .setStatus(alert.getStatus())
                .setDescription(alert.getDescription())
                .setLocation(location)
                .setReporterName(alert.getReporterName())
                .setTimestamp(alert.getTimestamp())
                .setAssignedTeam(alert.getAssignedTeam() != null ? alert.getAssignedTeam() : "")
                .setEstimatedArrivalTime(alert.getEstimatedArrivalTime())
                .build();
    }
}
