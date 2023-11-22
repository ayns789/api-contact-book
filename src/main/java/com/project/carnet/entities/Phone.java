package com.project.carnet.entities;

import com.project.carnet.enums.PhoneType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "phone")
@SequenceGenerator(name = "phone.seq_phone", sequenceName = "seq_phone", initialValue = 1, allocationSize = 1)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone.seq_phone")
    @Column(name = "phone_id", nullable = false)
    private long phoneId;

    @Column(name = "libelle", nullable = false)
    private Integer libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PhoneType type;

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

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
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
        return getPhoneId() == phone.getPhoneId() && Objects.equals(libelle, phone.libelle) && getType() == phone.getType() && Objects.equals(getContact(), phone.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneId(), libelle, getType(), getContact());
    }
}
