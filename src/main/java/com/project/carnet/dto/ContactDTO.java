package com.project.carnet.dto;

import com.project.carnet.entities.Address;
import com.project.carnet.entities.Civility;
import com.project.carnet.entities.Email;
import com.project.carnet.entities.Phone;

import java.util.List;

public class ContactDTO {

    private long contactId;

    private String firstName;

    private String lastName;

    private Civility civility;

    private Email primaryEmail;

    private List<Email> secondaryEmails;

    private Phone primaryPhone;

    private List<Phone> secondaryPhones;

    private Address primaryAddress;

    private List<Address> secondaryAddresses;

    public ContactDTO(){

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

    public Email getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(Email primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public List<Email> getSecondaryEmails() {
        return secondaryEmails;
    }

    public void setSecondaryEmails(List<Email> secondaryEmails) {
        this.secondaryEmails = secondaryEmails;
    }

    public Phone getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(Phone primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public List<Phone> getSecondaryPhones() {
        return secondaryPhones;
    }

    public void setSecondaryPhones(List<Phone> secondaryPhones) {
        this.secondaryPhones = secondaryPhones;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public List<Address> getSecondaryAddresses() {
        return secondaryAddresses;
    }

    public void setSecondaryAddresses(List<Address> secondaryAddresses) {
        this.secondaryAddresses = secondaryAddresses;
    }
}
