package com.project.carnet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "address")
public class Address extends AbstractEntityId{

    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;
    @Column(name = "street_type", nullable = false)
    private String streetType;
    @Column(name = "street_name", nullable = false)
    private String streetName;
    @Column(name = "city_name", nullable = false)
    private String cityName;
    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    protected Address(){

    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
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
}
