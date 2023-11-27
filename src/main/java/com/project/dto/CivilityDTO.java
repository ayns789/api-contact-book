package com.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CivilityDTO {
    private long civilityId;

    @NotNull
    @NotBlank
    @Max(value=20, message = "'type' of phone must be under 20 characters")
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
