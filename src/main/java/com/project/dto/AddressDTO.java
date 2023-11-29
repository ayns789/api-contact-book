package com.project.dto;

import com.project.entities.Contact;
import com.project.entities.Country;
import com.project.enums.StreetTypeEnum;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class AddressDTO {

    private long addressId;

    @NotNull
    @NotBlank
    @Size(max=15, message = "number of street must be under 15 characters")
    private Integer streetNumber;

    @NotNull
    @NotBlank
    @Size(max=50, message = "'type' of address must be under 50 characters")
    private StreetTypeEnum streetType;

    @NotNull
    @NotBlank
    @Size(max=255, message = "'streetName' of address must be under 255 characters")
    private String streetName;

    @NotNull
    @NotBlank
    @Size(max=255, message = "'cityName' of address must be under 255 characters")
    private String cityName;

    @NotNull
    @NotBlank
    @Size(max=25, message = "'postalCode' of address must be under 25 characters")
    private Integer postalCode;

    private ContactDTO contact;

    private CountryDTO country;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public StreetTypeEnum getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetTypeEnum streetType) {
        this.streetType = streetType;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressId=" + addressId +
                ", streetNumber=" + streetNumber +
                ", streetType=" + streetType +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postalCode=" + postalCode +
                ", contact=" + contact +
                ", country=" + country +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO that)) return false;

        if (addressId != that.addressId) return false;
        if (!Objects.equals(streetNumber, that.streetNumber)) return false;
        if (streetType != that.streetType) return false;
        if (!Objects.equals(streetName, that.streetName)) return false;
        if (!Objects.equals(cityName, that.cityName)) return false;
        if (!Objects.equals(postalCode, that.postalCode)) return false;
        if (!Objects.equals(contact, that.contact)) return false;
        return Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        int result = (int) (addressId ^ (addressId >>> 32));
        result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
        result = 31 * result + (streetType != null ? streetType.hashCode() : 0);
        result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
