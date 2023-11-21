package com.project.carnet.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "email")
public class Email extends AbstractEntityId {
    @Column(name = "libelle", nullable = false)
    private String emailLibelle;

    @Column(name = "type", nullable = false)
    private String emailType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    protected Email(){

    }

    public String getEmailLibelle() {
        return emailLibelle;
    }

    public void setEmailLibelle(String emailLibelle) {
        this.emailLibelle = emailLibelle;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
