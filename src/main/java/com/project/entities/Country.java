package com.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "country")
@SequenceGenerator(name = "CountryIdGenerator", sequenceName = "country_seq", allocationSize = 1)
@Getter
@Setter
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CountryIdGenerator")
    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Override
    public String toString() {
        return "Country{" +
            "countryId=" + countryId +
            ", libelle='" + libelle + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country country)) {
            return false;
        }
        return getCountryId() == country.getCountryId() && Objects.equals(getLibelle(), country.getLibelle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryId(), getLibelle());
    }
}
