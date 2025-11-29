package com.enicarthage.mobility.service;


import com.enicarthage.mobility.model.LigneBus;
import com.enicarthage.mobility.repositories.LigneBusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigneBusService {
    private final LigneBusRepository repo;

    public LigneBusService(LigneBusRepository repo) {
        this.repo = repo;
    }

    public List<LigneBus> findAll() {
        return repo.findAll();
    }

    public LigneBus findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ligne non trouvée: " + id));
    }

    public LigneBus save(LigneBus ligne) {
        return repo.save(ligne);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
