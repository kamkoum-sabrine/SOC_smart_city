package com.enicarthage.model;


import java.util.List;

public class ZoneInfo {
    private String zone;
    private Integer aqi;
    private String mainPollutant;
    private Boolean transportAvailable;
    private java.util.List<String> transportLines;
    private Integer activeEmergencies;

    public ZoneInfo() {
    }

    public ZoneInfo(String zone, Integer aqi, String mainPollutant, Boolean transportAvailable, List<String> transportLines, Integer activeEmergencies) {
        this.zone = zone;
        this.aqi = aqi;
        this.mainPollutant = mainPollutant;
        this.transportAvailable = transportAvailable;
        this.transportLines = transportLines;
        this.activeEmergencies = activeEmergencies;
    }

    public String getZone() {
        return zone;
    }

    public Integer getAqi() {
        return aqi;
    }

    public String getMainPollutant() {
        return mainPollutant;
    }

    public Boolean getTransportAvailable() {
        return transportAvailable;
    }

    public List<String> getTransportLines() {
        return transportLines;
    }

    public Integer getActiveEmergencies() {
        return activeEmergencies;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public void setMainPollutant(String mainPollutant) {
        this.mainPollutant = mainPollutant;
    }

    public void setTransportAvailable(Boolean transportAvailable) {
        this.transportAvailable = transportAvailable;
    }

    public void setTransportLines(List<String> transportLines) {
        this.transportLines = transportLines;
    }

    public void setActiveEmergencies(Integer activeEmergencies) {
        this.activeEmergencies = activeEmergencies;
    }

    @Override
    public String toString() {
        return "ZoneInfo{" +
                "zone='" + zone + '\'' +
                ", aqi=" + aqi +
                ", mainPollutant='" + mainPollutant + '\'' +
                ", transportAvailable=" + transportAvailable +
                ", transportLines=" + transportLines +
                ", activeEmergencies=" + activeEmergencies +
                '}';
    }
}
