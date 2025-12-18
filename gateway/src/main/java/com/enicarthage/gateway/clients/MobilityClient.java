package com.enicarthage.gateway.clients;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@Service
public class MobilityClient {

    private final WebClient web;

    public MobilityClient(WebClient.Builder builder) {
        this.web = builder.baseUrl("http://mobility-rest:8081/api/lines").build();
    }

    /**
     * GET /api/lines
     */
    public Mono<List<Map>> getAllLines() {
        return web.get()
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList();
    }

    /** GET /api/lines/{id} */
    public Mono<Map> getLineById(Long id) {
        return web.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /** POST /api/lines */
    public Mono<Map> createLine(Map<String, Object> dto) {
        return web.post()
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /** PUT /api/lines/{id} */
    public Mono<Map> updateLine(Long id, Map<String, Object> dto) {
        return web.put()
                .uri("/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Map.class);
    }

    /** DELETE /api/lines/{id} */
    public Mono<Void> deleteLine(Long id) {
        return web.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
