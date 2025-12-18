package com.enicarthage.model;

import java.util.List;

// simple DTO for route rec
public class RouteRecommendation {
    public String from, to, recommendedMode, reason;
    public java.util.List<String> availableLines;

    public RouteRecommendation() {
    }

    public RouteRecommendation(String to, String from, String recommendedMode, String reason, List<String> availableLines) {
        this.to = to;
        this.from = from;
        this.recommendedMode = recommendedMode;
        this.reason = reason;
        this.availableLines = availableLines;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getRecommendedMode() {
        return recommendedMode;
    }

    public List<String> getAvailableLines() {
        return availableLines;
    }

    public String getReason() {
        return reason;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRecommendedMode(String recommendedMode) {
        this.recommendedMode = recommendedMode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setAvailableLines(List<String> availableLines) {
        this.availableLines = availableLines;
    }
}
