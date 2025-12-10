package com.enicarthage.airquality.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "airquality")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema airQualitySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AirQualityPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://enicarthage.com/airquality");
        wsdl11Definition.setSchema(airQualitySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema airQualitySchema() {
        return new SimpleXsdSchema(new ClassPathResource("airquality.xsd"));
    }


    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        PayloadLoggingInterceptor loggingInterceptor = new PayloadLoggingInterceptor();
        loggingInterceptor.setLogRequest(true);
        loggingInterceptor.setLogResponse(true);
        interceptors.add(loggingInterceptor);
    }
}