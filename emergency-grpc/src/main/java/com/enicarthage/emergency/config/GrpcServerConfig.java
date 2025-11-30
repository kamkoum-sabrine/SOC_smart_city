package com.enicarthage.emergency.config;


import com.enicarthage.emergency.service.EmergencyServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(GrpcServerConfig.class);

    @Value("${grpc.server.port:9090}")
    private int grpcPort;

    private Server server;

    private final EmergencyServiceImpl emergencyService;

    public GrpcServerConfig(EmergencyServiceImpl emergencyService) {
        this.emergencyService = emergencyService;
    }

    @PostConstruct
    public void start() throws IOException {
        server = ServerBuilder.forPort(grpcPort)
                .addService(emergencyService)
                .build()
                .start();

        logger.info("gRPC Emergency Service démarré sur le port {}", grpcPort);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Arrêt du serveur gRPC...");
            stop();
        }));
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
            logger.info("Serveur gRPC arrêté");
        }
    }

    @Bean
    public Server grpcServer() {
        return server;
    }
}
