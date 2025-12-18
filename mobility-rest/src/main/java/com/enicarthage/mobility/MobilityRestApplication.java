
package com.enicarthage.mobility;

import com.enicarthage.mobility.repositories.LigneBusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.enicarthage.mobility.model.LigneBus;

@SpringBootApplication
public class MobilityRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobilityRestApplication.class, args);
    }

    // seed simple data for demo
    @Bean
    CommandLineRunner initData(LigneBusRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                LigneBus l1 = new LigneBus();
                l1.setCode("L1");
                l1.setNom("Ligne Centre - Sud");
                l1.setHorairesJson("[{\"heure\":\"08:00\"},{\"heure\":\"09:00\"}]");

                LigneBus l2 = new LigneBus();
                l2.setCode("L2");
                l2.setNom("Ligne Est - Ouest");
                l2.setHorairesJson("[{\"heure\":\"08:30\"},{\"heure\":\"10:30\"}]");

                repo.save(l1);
                repo.save(l2);
            }
        };
    }
}
