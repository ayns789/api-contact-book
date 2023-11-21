package com.project.carnet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "email")
public class Email extends AbstractEntityId {
    @Column(name = "libelle", nullable = false)
    private String emailLibelle;

    @Column(name = "type", nullable = false)
    private String emailType;

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
}
