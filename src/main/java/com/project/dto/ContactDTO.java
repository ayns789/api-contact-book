package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public class ContactDTO {

    private Long contactId;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "'firstname' of contact must be under 50 characters")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "'lastName' of contact must be under 50 characters")
    private String lastName;

    private CivilityDTO civility;

    private List<EmailDTO> emails;

    private List<PhoneDTO> phones;

    private List<AddressDTO> addresses;


    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
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

    public CivilityDTO getCivility() {
        return civility;
    }

    public void setCivility(CivilityDTO civility) {
        this.civility = civility;
    }

    public List<EmailDTO> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailDTO> emails) {
        this.emails = emails;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
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
        if (!(o instanceof ContactDTO that)) return false;

        if (contactId != that.contactId) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(civility, that.civility)) return false;
        if (!Objects.equals(emails, that.emails)) return false;
        if (!Objects.equals(phones, that.phones)) return false;
        return Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        int result = (int) (contactId ^ (contactId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (civility != null ? civility.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        result = 31 * result + (addresses != null ? addresses.hashCode() : 0);
        return result;
    }
}
