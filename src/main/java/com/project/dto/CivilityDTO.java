package com.project.dto;

import jakarta.validation.constraints.*;

public class CivilityDTO {
    private long civilityId;

    @NotNull
    @NotBlank
    @Size(max=20, message = "'libelle' of civility must be under 20 characters")
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
