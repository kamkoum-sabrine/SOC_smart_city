package com.enicarthage.gateway.controllers;

import com.enicarthage.gateway.clients.MobilityClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gateway/mobility")
public class MobilityGatewayController {

    private final MobilityClient client;

    public MobilityGatewayController(MobilityClient client) {
        this.client = client;
    }

    @GetMapping("/lines")
    public Mono<List<Map>> allLines() {
        return client.getAllLines();
    }

    @GetMapping("/lines/{id}")
    public Mono<Map> lineById(@PathVariable Long id) {
        return client.getLineById(id);
    }

    @PostMapping("/lines")
    public Mono<Map> create(@RequestBody Map<String, Object> dto) {
        return client.createLine(dto);
    }

    @PutMapping("/lines/{id}")
    public Mono<Map> update(@PathVariable Long id, @RequestBody Map<String, Object> dto) {
        return client.updateLine(id, dto);
    }

    @DeleteMapping("/lines/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return client.deleteLine(id);
    }
}

