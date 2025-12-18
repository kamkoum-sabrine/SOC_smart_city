package com.enicarthage.graphql;


import com.enicarthage.service.InfoService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;

@Controller
public class MutationResolver {
    private final InfoService infoService;

    public MutationResolver(InfoService infoService) {
        this.infoService = infoService;
    }

    @MutationMapping
    public String reportEmergency(@Argument String type, @Argument String description, @Argument double latitude, @Argument double longitude) {
        return infoService.reportEmergency(type, description, latitude, longitude);
    }
}
