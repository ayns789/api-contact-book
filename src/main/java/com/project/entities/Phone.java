package com.project.entities;

import com.project.enums.PhoneTypeEnum;
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
@Table(name = "phone")
@SequenceGenerator(name = "PhoneIdGenerator", sequenceName = "phone_seq", allocationSize = 1)
@Getter
@Setter
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PhoneIdGenerator")
    @Column(name = "phone_id", nullable = false)
    private Long phoneId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Override
    public String toString() {
        return "Phone{" +
            "phoneId=" + phoneId +
            ", libelle=" + libelle +
            ", type=" + type +
            ", contact=" + contact +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone phone)) {
            return false;
        }
        return getPhoneId() == phone.getPhoneId() && Objects.equals(getLibelle(), phone.getLibelle()) && getType() == phone.getType() &&
            Objects.equals(getContact(), phone.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneId(), getLibelle(), getType(), getContact());
    }
}
