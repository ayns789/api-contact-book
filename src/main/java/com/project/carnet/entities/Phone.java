package com.project.carnet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "phone")
public class Phone extends AbstractEntityId{
    @Column(name = "libelle", nullable = false)
    private Integer phoneLibelle;
    @Column(name = "type", nullable = false)
    private String phoneType;

    protected Phone() {

    }

    public Integer getPhoneLibelle() {
        return phoneLibelle;
    }

    public void setPhoneLibelle(Integer phoneLibelle) {
        this.phoneLibelle = phoneLibelle;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }
}
