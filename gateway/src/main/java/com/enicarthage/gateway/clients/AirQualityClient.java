package com.enicarthage.gateway.clients;


import com.enicarthage.airquality.xsd.GetAirQualityRequest;
import com.enicarthage.airquality.xsd.GetAirQualityResponse;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class AirQualityClient {

    private final WebServiceTemplate webServiceTemplate;

    public AirQualityClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public GetAirQualityResponse getAirQuality(String zone) {
        GetAirQualityRequest request = new GetAirQualityRequest();
        request.setZone(zone);

        // Appel SOAP
        return (GetAirQualityResponse) webServiceTemplate
                .marshalSendAndReceive("http://localhost:8085/ws", request);
    }
}

