package com.enicarthage.emergency.repository;

import com.enicarthage.emergency.model.EmergencyAlert;
import com.enicarthage.emergency.grpc.AlertStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmergencyRepository {

    //map pour stocker les alertes
    private final Map<String, EmergencyAlert> alerts = new ConcurrentHashMap<>();

    public EmergencyAlert save(EmergencyAlert alert) {
        alerts.put(alert.getAlertId(), alert);
        return alert;
    }

    public Optional<EmergencyAlert> findById(String alertId) {
        return Optional.ofNullable(alerts.get(alertId));
    }

    public List<EmergencyAlert> findAllActive() {
        return alerts.values().stream()
                .filter(alert -> alert.getStatus() != AlertStatus.RESOLVED
                        && alert.getStatus() != AlertStatus.CANCELLED)
                .collect(Collectors.toList());
    }

    public List<EmergencyAlert> findAll() {
        return new ArrayList<>(alerts.values());
    }

    public void deleteById(String alertId) {
        alerts.remove(alertId);
    }

    public long countActive() {
        return alerts.values().stream()
                .filter(alert -> alert.getStatus() != AlertStatus.RESOLVED
                        && alert.getStatus() != AlertStatus.CANCELLED)
                .count();
    }
}
