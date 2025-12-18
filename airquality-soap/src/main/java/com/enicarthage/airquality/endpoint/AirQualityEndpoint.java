package com.enicarthage.airquality.endpoint;

import com.enicarthage.airquality.model.AirQuality;
import com.enicarthage.airquality.xsd.GetAirQualityRequest;
import com.enicarthage.airquality.xsd.GetAirQualityResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.Map;

@Endpoint
public class AirQualityEndpoint {

    private static final String NAMESPACE_URI = "http://enicarthage.com/airquality";

    private static final Map<String, AirQuality> DATA = new HashMap<>();

    static {
        DATA.put("Centre", new AirQuality("Centre", 75, "NO2"));
        DATA.put("Nord", new AirQuality("Nord", 55, "PM2.5"));
        DATA.put("Sud", new AirQuality("Sud", 95, "O3"));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAirQualityRequest")
    @ResponsePayload
    public GetAirQualityResponse getAirQuality(@RequestPayload GetAirQualityRequest request) {
        System.out.println("=== REQUETE SOAP RECUE ===");
        System.out.println("Zone demandée: " + request.getZone());

        GetAirQualityResponse response = new GetAirQualityResponse();

        AirQuality airQuality = DATA.get(request.getZone());
        if (airQuality != null) {
            response.setZone(airQuality.getZone());
            response.setAQI(airQuality.getAQI());
            response.setMainPollutant(airQuality.getMainPollutant());
        } else {
            response.setZone(request.getZone());
            response.setAQI(50);
            response.setMainPollutant("CO2");
        }

        System.out.println("Réponse envoyée: " + response.getZone() + " - " + response.getAQI());
        return response;
    }
}