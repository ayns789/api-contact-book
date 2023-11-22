package com.project.carnet.entities;

import com.project.carnet.enums.EmailTypeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "email")
@SequenceGenerator(name = "email.seq_email", sequenceName = "seq_email", allocationSize = 1)
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email.seq_phone")
    @Column(name = "email_id", nullable = false)
    private long emailId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "type", nullable = false)
    private EmailTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contactId;

    protected Email(){

    }

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

    public Contact getContactId() {
        return contactId;
    }

    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailId=" + emailId +
                ", libelle='" + libelle + '\'' +
                ", type=" + type +
                ", contactId=" + contactId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return getEmailId() == email.getEmailId() && Objects.equals(getLibelle(), email.getLibelle()) && getType() == email.getType() && Objects.equals(getContactId(), email.getContactId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmailId(), getLibelle(), getType(), getContactId());
    }
}
