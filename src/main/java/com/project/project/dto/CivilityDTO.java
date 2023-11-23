package com.project.project.dto;

public class CivilityDTO {
    private long civilityId;

    private String libelle;

    public CivilityDTO(){

    }

    public long getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(long civilityId) {
        this.civilityId = civilityId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
