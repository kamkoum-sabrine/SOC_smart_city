package com.enicarthage.gateway.clients;

import com.enicarthage.airquality.xsd.GetAirQualityRequest;
import com.enicarthage.airquality.xsd.GetAirQualityResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class AirQualityClient {

    private final WebServiceTemplate webServiceTemplate;

    public AirQualityClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public GetAirQualityResponse getAirQuality(String zone) {
        GetAirQualityRequest request = new GetAirQualityRequest();
        request.setZone(zone);

        return (GetAirQualityResponse) webServiceTemplate.marshalSendAndReceive(request);
    }
}

