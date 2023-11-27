package com.project.dto;


import jakarta.validation.constraints.*;

public class CountryDTO {

    private long countryId;

    @NotNull
    @NotBlank
    @Size(max=100, message = "'libelle' of country must be under 100 characters")
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
