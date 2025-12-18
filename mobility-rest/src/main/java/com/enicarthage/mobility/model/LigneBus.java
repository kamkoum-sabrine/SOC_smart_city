package com.enicarthage.mobility.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lignes_bus")
public class LigneBus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String nom;

    // horaires stored as JSON string for prototype
    @Column(columnDefinition = "TEXT")
    private String horairesJson;

    public LigneBus() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getHorairesJson() { return horairesJson; }
    public void setHorairesJson(String horairesJson) { this.horairesJson = horairesJson; }
}
