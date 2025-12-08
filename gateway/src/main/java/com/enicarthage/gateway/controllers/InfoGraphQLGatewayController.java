package com.enicarthage.gateway.controllers;

import com.enicarthage.gateway.clients.InfoGraphQLClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/gateway")
public class InfoGraphQLGatewayController {

    private final InfoGraphQLClient graphQLClient;

    public InfoGraphQLGatewayController(InfoGraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
    }

    // Exemple: GET /gateway/zones?filter=Centre&minAqi=50
    @GetMapping("/zones")
    public Mono<Map> getZones(@RequestParam(required = false) String filter,
                              @RequestParam(required = false) Integer minAqi) {
        String query = """
            query($filter: String, $minAqi: Int) {
              zones(filter: $filter, minAqi: $minAqi) {
                zone
                aqi
                mainPollutant
                transportAvailable
                transportLines
                activeEmergencies
              }
            }
        """;
        return graphQLClient.executeQuery(query, Map.of("filter", filter, "minAqi", minAqi));
    }

    // Exemple: GET /gateway/planRoute?from=A&to=B&prefer=fast
    @GetMapping("/planRoute")
    public Mono<Map> planRoute(@RequestParam String from,
                               @RequestParam String to,
                               @RequestParam(required = false) String prefer) {
        String query = """
            query($from: String!, $to: String!, $prefer: String) {
              planRoute(from: $from, to: $to, prefer: $prefer) {
                from
                to
                recommendedMode
                reason
                availableLines
              }
            }
        """;
        return graphQLClient.executeQuery(query, Map.of("from", from, "to", to, "prefer", prefer));
    }
}

