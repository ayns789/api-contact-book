package com.project.entities;

import com.project.enums.StreetTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
@SequenceGenerator(name = "AddressIdGenerator", sequenceName = "address_seq", allocationSize = 1)
@Getter
@Setter
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AddressIdGenerator")
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

    @Column(name = "street_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StreetTypeEnum streetType;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Override
    public String toString() {
        return "Address{" +
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address address)) {
            return false;
        }
        return getAddressId() == address.getAddressId() && Objects.equals(getStreetNumber(), address.getStreetNumber()) &&
            getStreetType() == address.getStreetType() && Objects.equals(getStreetName(), address.getStreetName()) &&
            Objects.equals(getCityName(), address.getCityName()) && Objects.equals(getPostalCode(), address.getPostalCode()) &&
            Objects.equals(getContact(), address.getContact()) && Objects.equals(getCountry(), address.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressId(), getStreetNumber(), getStreetType(), getStreetName(), getCityName(), getPostalCode(), getContact(),
            getCountry());
    }
}
