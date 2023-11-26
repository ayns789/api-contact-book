package com.project.dto;

import com.project.entities.Contact;
import com.project.enums.EmailTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class EmailDTO {

    private long emailId;

    private String libelle;

    @Enumerated(EnumType.STRING)
    private EmailTypeEnum type;

    private Contact contact;

    public long getEmailId() {
        return emailId;
    }

    public void setEmailId(long emailId) {
        this.emailId = emailId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public EmailTypeEnum getType() {
        return type;
    }

    public void setType(EmailTypeEnum type) {
        this.type = type;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
