package com.project.carnet.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contact")
@SequenceGenerator(name = "contact.seq_contact", sequenceName = "seq_contact", allocationSize = 1)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact.seq_contact")
    @Column(name = "contact_id", nullable = false)
    private long contactId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "civility_id")
    private Civility civilityId;

    @OneToMany(mappedBy = "contact")
    @JoinColumn(name = "email_id")
    private List<Email> emailsId = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    @JoinColumn(name = "phone_id")
    private List<Phone> phonesId = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    @JoinColumn(name = "address_id")
    private List<Address> addressesId = new ArrayList<>();

    protected Contact() {

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

    public Civility getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(Civility civilityId) {
        this.civilityId = civilityId;
    }

    public List<Email> getEmailsId() {
        return emailsId;
    }

    public void setEmailsId(List<Email> emailsId) {
        this.emailsId = emailsId;
    }

    public List<Phone> getPhonesId() {
        return phonesId;
    }

    public void setPhonesId(List<Phone> phonesId) {
        this.phonesId = phonesId;
    }

    public List<Address> getAddressesId() {
        return addressesId;
    }

    public void setAddressesId(List<Address> addressesId) {
        this.addressesId = addressesId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", civilityId=" + civilityId +
                ", emailsId=" + emailsId +
                ", phonesId=" + phonesId +
                ", addressesId=" + addressesId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return getContactId() == contact.getContactId() && Objects.equals(getFirstName(), contact.getFirstName()) && Objects.equals(getLastName(), contact.getLastName()) && Objects.equals(getCivilityId(), contact.getCivilityId()) && Objects.equals(getEmailsId(), contact.getEmailsId()) && Objects.equals(getPhonesId(), contact.getPhonesId()) && Objects.equals(getAddressesId(), contact.getAddressesId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getFirstName(), getLastName(), getCivilityId(), getEmailsId(), getPhonesId(), getAddressesId());
    }
}

