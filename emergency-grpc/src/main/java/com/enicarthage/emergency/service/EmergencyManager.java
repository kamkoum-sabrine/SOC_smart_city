package com.enicarthage.emergency.service;


import com.enicarthage.emergency.model.EmergencyAlert;
import com.enicarthage.emergency.repository.EmergencyRepository;
import com.enicarthage.emergency.grpc.AlertStatus;
import com.enicarthage.emergency.grpc.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmergencyManager {

    private static final Logger logger = LoggerFactory.getLogger(EmergencyManager.class);
    private final EmergencyRepository repository;

    public EmergencyManager(EmergencyRepository repository) {
        this.repository = repository;
    }

    public String generateAlertId() {
        return "ALT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public EmergencyAlert createAlert(EmergencyAlert alert) {
        // 1. Générer un ID unique
        alert.setAlertId(generateAlertId());

        // 2. Définir le statut initial
        alert.setStatus(AlertStatus.PENDING);

        EmergencyAlert saved = repository.save(alert);
        logger.info("Nouvelle alerte créée: {} - Type: {} - Priorité: {}",
                saved.getAlertId(), saved.getType(), saved.getPriority());

        return saved;
    }

    public EmergencyAlert updateStatus(String alertId, AlertStatus newStatus, String notes) {
        EmergencyAlert alert = repository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException("Alerte non trouvée: " + alertId));

        alert.setStatus(newStatus);
        repository.save(alert);

        logger.info("Statut mis à jour pour {}: {} ({})", alertId, newStatus, notes);
        return alert;
    }

    public EmergencyAlert dispatchTeam(String alertId, String teamId, int eta) {
        //recuperer l'alerte
        EmergencyAlert alert = repository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException("Alerte non trouvée: " + alertId));

        //assigner à l'équipe
        alert.setAssignedTeam(teamId);
        alert.setEstimatedArrivalTime(eta);
        alert.setStatus(AlertStatus.DISPATCHED);

        repository.save(alert);

        logger.info("Équipe {} dispatchée pour {} - ETA: {} min", teamId, alertId, eta);
        return alert;
    }

    public int calculateETA(Priority priority, String zone) {
        // Simulation simple du calcul d'ETA
        int baseTime = switch (zone.toLowerCase()) {
            case "centre" -> 5;
            case "nord", "sud" -> 8;
            case "est", "ouest" -> 10;
            default -> 12;
        };

        int priorityMultiplier = switch (priority) {
            case CRITICAL -> 1;
            case HIGH -> 2;
            case MEDIUM -> 3;
            case LOW -> 4;
            default -> 3;
        };

        return baseTime * priorityMultiplier;
    }
}
