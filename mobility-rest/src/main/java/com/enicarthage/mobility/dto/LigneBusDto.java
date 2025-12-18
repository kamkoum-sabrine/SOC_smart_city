package com.enicarthage.mobility.dto;

import jakarta.validation.constraints.NotBlank;

public class LigneBusDto {
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String nom;

    private String horairesJson;

    public LigneBusDto() {}

    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getCode(){return code;}
    public void setCode(String code){this.code = code;}
    public String getNom(){return nom;}
    public void setNom(String nom){this.nom = nom;}
    public String getHorairesJson(){return horairesJson;}
    public void setHorairesJson(String horairesJson){this.horairesJson = horairesJson;}
}

