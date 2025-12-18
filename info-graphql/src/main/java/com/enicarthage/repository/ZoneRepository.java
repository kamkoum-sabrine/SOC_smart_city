package com.enicarthage.repository;

import com.enicarthage.model.ZoneInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ZoneRepository {
    private final Map<String, ZoneInfo> map = new HashMap<>();

    @PostConstruct
    public void init() {
        ZoneInfo z = new ZoneInfo();
        z.setZone("Centre");
        z.setAqi(60);
        z.setMainPollutant("NO2");
        z.setTransportAvailable(true);
        z.setTransportLines(Arrays.asList("Bus 1", "Bus 2"));
        z.setActiveEmergencies(1);
        map.put(z.getZone().toLowerCase(), z);
        // add more sample zones
    }

    public Optional<ZoneInfo> findByZone(String zone) {
        return Optional.ofNullable(map.get(zone.toLowerCase()));
    }

    public List<ZoneInfo> findAll() {
        return new ArrayList<>(map.values());
    }
}
