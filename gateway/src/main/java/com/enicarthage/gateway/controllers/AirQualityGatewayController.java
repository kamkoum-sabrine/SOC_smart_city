package com.enicarthage.gateway.controllers;


import com.enicarthage.airquality.xsd.GetAirQualityResponse;
import com.enicarthage.gateway.clients.AirQualityClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/airquality")
public class AirQualityGatewayController {

    private final AirQualityClient client;

    public AirQualityGatewayController(AirQualityClient client) {
        this.client = client;
    }

    @GetMapping("/{zone}")
    public Map<String, Object> getAirQuality(@PathVariable String zone) {
        GetAirQualityResponse response = client.getAirQuality(zone);

        Map<String, Object> map = new HashMap<>();
        map.put("zone", response.getZone());
        map.put("AQI", response.getAQI());
        map.put("mainPollutant", response.getMainPollutant());

        return map;
    }
}

