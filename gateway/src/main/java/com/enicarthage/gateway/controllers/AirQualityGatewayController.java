package com.enicarthage.gateway.controllers;

import com.enicarthage.airquality.xsd.GetAirQualityResponse;
import com.enicarthage.gateway.clients.AirQualityClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/airquality")
public class AirQualityGatewayController {

    private final AirQualityClient client;

    public AirQualityGatewayController(AirQualityClient client) {
        this.client = client;
    }

    @GetMapping("/{zone}")
    public GetAirQualityResponse getZone(@PathVariable String zone) {
        return client.getAirQuality(zone);
    }
}
