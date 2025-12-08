package com.enicarthage.gateway.clients;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class InfoGraphQLClient {

    private final WebClient webClient;

    public InfoGraphQLClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8084/graphql").build();
    }

    // Exécution d'une requête GraphQL générique
    public Mono<Map> executeQuery(String query, Map<String, Object> variables) {
        return webClient.post()
                .bodyValue(Map.of("query", query, "variables", variables))
                .retrieve()
                .bodyToMono(Map.class);
    }

}
