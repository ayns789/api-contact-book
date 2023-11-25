package com.project.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contact")
@SequenceGenerator(name = "ContactIdGenerator", sequenceName = "contact_seq", allocationSize = 1)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContactIdGenerator")
    @Column(name = "contact_id", nullable = false)
    private long contactId;

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

    public Contact() {

    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Civility getCivility() {
        return civility;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

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
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return getContactId() == contact.getContactId() && Objects.equals(getFirstName(), contact.getFirstName()) && Objects.equals(getLastName(), contact.getLastName()) && Objects.equals(getCivility(), contact.getCivility()) && Objects.equals(getEmails(), contact.getEmails()) && Objects.equals(getPhones(), contact.getPhones()) && Objects.equals(getAddresses(), contact.getAddresses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getFirstName(), getLastName(), getCivility(), getEmails(), getPhones(), getAddresses());
    }
}

