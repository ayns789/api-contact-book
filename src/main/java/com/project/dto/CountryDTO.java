package com.project.dto;


import jakarta.validation.constraints.*;

import java.util.Objects;

public class CountryDTO {

    private long countryId;

    @NotNull
    @NotBlank
    @Size(max=100, message = "'libelle' of country must be under 100 characters")
    private String libelle;

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

    @Override
    public String toString() {
        return "CountryDTO{" +
                "countryId=" + countryId +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryDTO that)) return false;

        if (countryId != that.countryId) return false;
        return Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        int result = (int) (countryId ^ (countryId >>> 32));
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
