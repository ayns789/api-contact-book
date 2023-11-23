package com.project.carnet.dto;

import com.project.carnet.entities.Contact;
import com.project.carnet.entities.Country;
import com.project.carnet.enums.StreetTypeEnum;

public class AddressDTO {

    private long addressId;

    private Integer streetNumber;

    private StreetTypeEnum streetType;

    private String streetName;

    private String cityName;

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
