package com.project.dto;

import com.project.entities.Contact;
import com.project.entities.Country;
import com.project.enums.StreetTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDTO {

    private long addressId;

    @NotNull
    @NotBlank
    private Integer streetNumber;

    @NotNull
    @NotBlank
    @Max(value=50, message = "'type' of phone must be under 50 characters")
    private StreetTypeEnum streetType;

    @NotNull
    @NotBlank
    @Max(value=255, message = "'type' of phone must be under 255 characters")
    private String streetName;

    @NotNull
    @NotBlank
    @Max(value=255, message = "'type' of phone must be under 255 characters")
    private String cityName;

    @NotNull
    @NotBlank
    private Integer postalCode;

    private Contact contact;

    private Country country;

    public AddressDTO(){

    }

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
