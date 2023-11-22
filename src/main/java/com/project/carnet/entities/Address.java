package com.project.carnet.entities;

import com.project.carnet.enums.StreetTypeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "address")
@SequenceGenerator(name = "address.seq_address", sequenceName = "seq_address", allocationSize = 1)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address.seq_address")
    @Column(name = "address_id", nullable = false)
    private long addressId;

    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

    @Column(name = "street_type", nullable = false)
    private StreetTypeEnum streetType;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country countryId;

    protected Address(){

    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public StreetTypeEnum getStreetType() {
        return streetType;
    }

    public void setStreetType(StreetTypeEnum streetType) {
        this.streetType = streetType;
    }

    public Contact getContactId() {
        return contactId;
    }

    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
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

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", streetNumber=" + streetNumber +
                ", streetType=" + streetType +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postalCode=" + postalCode +
                ", contactId=" + contactId +
                ", countryId=" + countryId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return getAddressId() == address.getAddressId() && Objects.equals(getStreetNumber(), address.getStreetNumber()) && getStreetType() == address.getStreetType() && Objects.equals(getStreetName(), address.getStreetName()) && Objects.equals(getCityName(), address.getCityName()) && Objects.equals(getPostalCode(), address.getPostalCode()) && Objects.equals(getContactId(), address.getContactId()) && Objects.equals(getCountryId(), address.getCountryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressId(), getStreetNumber(), getStreetType(), getStreetName(), getCityName(), getPostalCode(), getContactId(), getCountryId());
    }
}
