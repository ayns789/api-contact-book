package com.project.carnet.entities;

import com.project.carnet.enums.PhoneType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_phone")
    @SequenceGenerator(name = "seq_phone", sequenceName = "seq_phone", initialValue = 1, allocationSize = 1)
    private long phoneId;

    @Column(name = "libelle", nullable = false, length = 25)
    private Integer phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PhoneType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    protected Phone() {

    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneId=" + phoneId +
                ", phone=" + phone +
                ", type=" + type +
                ", contact=" + contact +
                '}';
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;
        return phoneId == phone.phoneId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneId);
    }
}
