package com.project.domain.entities;

import com.project.domain.enums.PhoneTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "phone")
@SequenceGenerator(name = "PhoneIdGenerator", sequenceName = "phone_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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
        return getPhoneId().equals(phone.getPhoneId()) && Objects.equals(getLibelle(), phone.getLibelle()) && getType() == phone.getType() &&
                Objects.equals(getContact(), phone.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneId(), getLibelle(), getType(), getContact());
    }
}
