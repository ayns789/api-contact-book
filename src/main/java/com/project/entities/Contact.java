package com.project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contact")
@SequenceGenerator(name = "ContactIdGenerator", sequenceName = "contact_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContactIdGenerator")
    @Column(name = "contact_id", nullable = false)
    private Long contactId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "civility_id")
    private Civility civility;

    @OneToMany(mappedBy = "contact")
    private List<Email> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    private List<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    private List<Address> addresses = new ArrayList<>();

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", civility=" + civility +
                ", emails=" + emails +
                ", phones=" + phones +
                ", addresses=" + addresses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact contact)) {
            return false;
        }
        return getContactId().equals(contact.getContactId()) && Objects.equals(getFirstName(), contact.getFirstName()) &&
                Objects.equals(getLastName(), contact.getLastName()) && Objects.equals(getCivility(), contact.getCivility()) &&
                Objects.equals(getEmails(), contact.getEmails()) && Objects.equals(getPhones(), contact.getPhones()) &&
                Objects.equals(getAddresses(), contact.getAddresses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getFirstName(), getLastName(), getCivility(), getEmails(), getPhones(), getAddresses());
    }
}

