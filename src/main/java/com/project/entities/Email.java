package com.project.entities;

import com.project.enums.EmailTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "email")
@SequenceGenerator(name = "EmailIdGenerator", sequenceName = "email_seq", allocationSize = 1)
@Getter
@Setter
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmailIdGenerator")
    @Column(name = "email_id", nullable = false)
    private Long emailId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email email)) {
            return false;
        }
        return getEmailId() == email.getEmailId() && Objects.equals(getLibelle(), email.getLibelle()) && getType() == email.getType() &&
            Objects.equals(getContact(), email.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmailId(), getLibelle(), getType(), getContact());
    }
}
