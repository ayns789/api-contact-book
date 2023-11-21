package com.project.carnet.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "phone")
public class Phone extends AbstractEntityId{
    @Column(name = "libelle", nullable = false)
    private Integer phoneLibelle;
    @Column(name = "type", nullable = false)
    private String phoneType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
