package com.project.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "country")
@SequenceGenerator(name = "CountryIdGenerator", sequenceName = "country_seq", allocationSize = 1)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CountryIdGenerator")
    @Column(name = "country_id", nullable = false)
    private long countryId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    protected Country(){

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

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryId +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country country)) return false;
        return getCountryId() == country.getCountryId() && Objects.equals(getLibelle(), country.getLibelle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryId(), getLibelle());
    }
}
