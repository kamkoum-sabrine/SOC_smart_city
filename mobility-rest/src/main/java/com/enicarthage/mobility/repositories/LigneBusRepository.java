package com.enicarthage.mobility.repositories;


import com.enicarthage.mobility.model.LigneBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneBusRepository extends JpaRepository<LigneBus, Long> {
    // extensions: findByCode, findByNomContaining, etc.
}

