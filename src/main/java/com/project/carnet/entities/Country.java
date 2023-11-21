package com.project.carnet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "country")
public class Country {

    @Column(name = "libelle", nullable = false)
    private String countryLibelle;

    protected Country(){

    }

    public String getCountryLibelle() {
        return countryLibelle;
    }

    public void setCountryLibelle(String countryLibelle) {
        this.countryLibelle = countryLibelle;
    }
}
