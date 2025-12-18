package com.enicarthage.graphql;


import com.enicarthage.model.RouteRecommendation;
import com.enicarthage.model.ZoneInfo;
import com.enicarthage.service.InfoService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;

@Controller
public class QueryResolver {

    private final InfoService infoService;

    public QueryResolver(InfoService infoService) {
        this.infoService = infoService;
    }

    @QueryMapping
    public ZoneInfo zoneInfo(@Argument String zone) {
        return infoService.getZoneInfo(zone);
    }

    @QueryMapping
    public List<ZoneInfo> zones(@Argument String filter, @Argument Integer minAqi) {
        return infoService.searchZones(filter, minAqi);
    }

    @QueryMapping
    public RouteRecommendation planRoute(@Argument String from, @Argument String to, @Argument String prefer) {
        return infoService.planRoute(from, to, prefer);
    }
}
