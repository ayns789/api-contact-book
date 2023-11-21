package com.project.carnet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "civility")
public class Civility extends AbstractEntityId {

    @Column(name = "libelle")
    private String civLibelle;

    protected Civility() {

    }

    public String getCivLibelle() {
        return civLibelle;
    }

    public void setCivLibelle(String civLibelle) {
        this.civLibelle = civLibelle;
    }

}
