package com.project.carnet.entities;

import com.project.carnet.enums.PhoneTypeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "phone")
@SequenceGenerator(name = "phone.seq_phone", sequenceName = "seq_phone", allocationSize = 1)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone.seq_phone")
    @Column(name = "phone_id", nullable = false)
    private long phoneId;

    @Column(name = "libelle", nullable = false)
    private Integer libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PhoneTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contactId;

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

    public Contact getContactId() {
        return contactId;
    }

    public void setContactId(Contact contactId) {
        this.contactId = contactId;
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
                ", contactId=" + contactId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;
        return getPhoneId() == phone.getPhoneId() && Objects.equals(getLibelle(), phone.getLibelle()) && getType() == phone.getType() && Objects.equals(getContactId(), phone.getContactId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneId(), getLibelle(), getType(), getContactId());
    }
}
