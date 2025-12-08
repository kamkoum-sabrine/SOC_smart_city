package com.enicarthage.gateway.config;

import jakarta.xml.soap.MessageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
public class GatewayConfig {

    @Bean
    public SaajSoapMessageFactory messageFactory() throws Exception {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
        messageFactory.afterPropertiesSet();
        return messageFactory;
    }

    /*@Bean
    public WebServiceTemplate webServiceTemplate(SaajSoapMessageFactory messageFactory) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMessageFactory(messageFactory);
        template.setDefaultUri("http://localhost:8082/ws"); // SOAP AirQuality
        return template;
    }*/
}
