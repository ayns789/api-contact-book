package com.project.entities;

import com.project.enums.PhoneTypeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "phone")
@SequenceGenerator(name = "PhoneIdGenerator", sequenceName = "phone_seq", allocationSize = 1)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PhoneIdGenerator")
    @Column(name = "phone_id", nullable = false)
    private long phoneId;

    @Column(name = "libelle", nullable = false)
    private Integer libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PhoneTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    protected Phone() {

    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getLibelle() {
        return libelle;
    }

    public void setLibelle(Integer libelle) {
        this.libelle = libelle;
    }


    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PhoneTypeEnum getType() {
        return type;
    }

    public void setType(PhoneTypeEnum type) {
        this.type = type;
    }

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
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;
        return getPhoneId() == phone.getPhoneId() && Objects.equals(getLibelle(), phone.getLibelle()) && getType() == phone.getType() && Objects.equals(getContact(), phone.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneId(), getLibelle(), getType(), getContact());
    }
}
