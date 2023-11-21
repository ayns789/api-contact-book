package com.project.carnet.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contact")
    @SequenceGenerator(name = "seq_contact", sequenceName = "seq_contact", initialValue = 1, allocationSize = 1)
    private long contactId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "civility_id")
    private Civility civility;

    @OneToMany(mappedBy = "contact")
    @JoinColumn(name = "email_id")
    private List<Email> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    @JoinColumn(name = "phone_id")
    private List<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    @JoinColumn(name = "address_id")
    private List<Address> addresses = new ArrayList<>();

    protected Contact() {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return getContactId() == contact.getContactId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId());
    }
}
