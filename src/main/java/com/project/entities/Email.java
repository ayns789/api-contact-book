package com.project.entities;

import com.project.enums.EmailTypeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "email")
@SequenceGenerator(name = "EmailIdGenerator", sequenceName = "email_seq", allocationSize = 1)
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmailIdGenerator")
    @Column(name = "email_id", nullable = false)
    private long emailId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "type", nullable = false)
    private EmailTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailId=" + emailId +
                ", libelle='" + libelle + '\'' +
                ", type=" + type +
                ", contact=" + contact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return getEmailId() == email.getEmailId() && Objects.equals(getLibelle(), email.getLibelle()) && getType() == email.getType() && Objects.equals(getContact(), email.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmailId(), getLibelle(), getType(), getContact());
    }
}
