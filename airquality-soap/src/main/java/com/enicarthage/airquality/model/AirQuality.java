package com.enicarthage.airquality.model;

public class AirQuality {
    private String zone;
    private int AQI;
    private String mainPollutant;

    public AirQuality(String zone, int AQI, String mainPollutant) {
        this.zone = zone;
        this.AQI = AQI;
        this.mainPollutant = mainPollutant;
    }

    public String getZone() { return zone; }
    public int getAQI() { return AQI; }
    public String getMainPollutant() { return mainPollutant; }
}