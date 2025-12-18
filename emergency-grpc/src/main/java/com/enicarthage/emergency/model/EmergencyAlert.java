package com.enicarthage.emergency.model;


import com.enicarthage.emergency.grpc.AlertStatus;
import com.enicarthage.emergency.grpc.EmergencyType;
import com.enicarthage.emergency.grpc.Priority;

import java.time.Instant;

public class EmergencyAlert {
    private String alertId;
    private EmergencyType type;
    private Priority priority;
    private AlertStatus status;
    private String description;
    private double latitude;
    private double longitude;
    private String address;
    private String zone;
    private String reporterName;
    private String reporterPhone;
    private long timestamp;
    private String assignedTeam;
    private int estimatedArrivalTime;

    public EmergencyAlert() {
        this.timestamp = Instant.now().toEpochMilli();
        this.status = AlertStatus.PENDING;
    }

    // Getters and Setters
    public String getAlertId() { return alertId; }
    public void setAlertId(String alertId) { this.alertId = alertId; }

    public EmergencyType getType() { return type; }
    public void setType(EmergencyType type) { this.type = type; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public AlertStatus getStatus() { return status; }
    public void setStatus(AlertStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getReporterPhone() { return reporterPhone; }
    public void setReporterPhone(String reporterPhone) { this.reporterPhone = reporterPhone; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getAssignedTeam() { return assignedTeam; }
    public void setAssignedTeam(String assignedTeam) { this.assignedTeam = assignedTeam; }

    public int getEstimatedArrivalTime() { return estimatedArrivalTime; }
    public void setEstimatedArrivalTime(int estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }
}
