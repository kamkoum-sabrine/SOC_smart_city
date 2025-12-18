package com.enicarthage.service;


import com.enicarthage.model.RouteRecommendation;
import com.enicarthage.model.ZoneInfo;
import com.enicarthage.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class InfoService {

    private final ZoneRepository zoneRepository;
    private final WebClient webClient; // used to call REST/orchestrator

    public InfoService(ZoneRepository zoneRepository, WebClient.Builder webClientBuilder) {
        this.zoneRepository = zoneRepository;
        this.webClient = webClientBuilder.build();
    }

    public ZoneInfo getZoneInfo(String zone) {
        // 1) local repo quickly returns basic data
        ZoneInfo base = zoneRepository.findByZone(zone).orElse(null);
        if (base == null) return null;

        // 2) Optionally call SOAP or REST to refresh AQI (example calling orchestrator REST at /orchestrator/zone/{zone})
        try {
            // example synchronous call (block) — ok for simple flows; can be reactive if desired
            Mono<ZoneInfo> remote = webClient.get()
                    .uri("http://localhost:8085/orchestrator/zone/{zone}", zone)
                    .retrieve()
                    .bodyToMono(ZoneInfo.class);
            ZoneInfo updated = remote.block(); // small demo; avoid block in reactive apps
            if (updated != null) {
                // merge fields
                base.setAqi(updated.getAqi());
                base.setMainPollutant(updated.getMainPollutant());
                base.setTransportAvailable(updated.getTransportAvailable());
                base.setTransportLines(updated.getTransportLines());
                base.setActiveEmergencies(updated.getActiveEmergencies());
            }
        } catch (Exception e) {
            // ignore remote call error; return best-effort local info
        }

        return base;
    }

    public List<ZoneInfo> searchZones(String filter, Integer minAqi) {
        java.util.List<ZoneInfo> all = zoneRepository.findAll();
        return all.stream()
                .filter(z -> filter == null || z.getZone().toLowerCase().contains(filter.toLowerCase()))
                .filter(z -> minAqi == null || (z.getAqi()!=null && z.getAqi() >= minAqi))
                .toList();
    }

    public String reportEmergency(String type, String desc, double lat, double lon) {
        // example: call orchestrator endpoint that forwards to gRPC service
        try {
            String id = webClient.post()
                    .uri("http://localhost:8085/orchestrator/emergency")
                    .bodyValue(java.util.Map.of(
                            "type", type,
                            "description", desc,
                            "latitude", lat,
                            "longitude", lon))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return id;
        } catch (Exception e) {
            // fallback
            return "ERROR";
        }
    }

    public RouteRecommendation planRoute(String from, String to, String prefer) {
        // either call orchestrator which aggregates SOAP + REST
        RouteRecommendation r = new RouteRecommendation();
        r.setFrom(from); r.setTo(to);
        // simplified
        r.setRecommendedMode("BUS");
        r.setReason("Good air quality & direct connection");
        r.setAvailableLines(java.util.List.of("Bus 1", "Metro A"));
        return r;
    }


}
