package com.project.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CountryDTO {

    private long countryId;

    @NotNull
    @NotBlank
    @Max(value=100, message = "'type' of phone must be under 100 characters")
    private String libelle;

    public CountryDTO(){

    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
