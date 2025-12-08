package com.enicarthage.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
public class GatewayConfig {

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(SaajSoapMessageFactory messageFactory) {
        return new WebServiceTemplate(messageFactory);
    }


}
