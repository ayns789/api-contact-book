package com.project.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "country")
@SequenceGenerator(name = "CountryIdGenerator", sequenceName = "country_seq", allocationSize = 1)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CountryIdGenerator")
    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Override
    public String toString() {
        return STR."Country{countryId=\{countryId}, libelle='\{libelle}\{'\''}\{'}'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country country)) {
            return false;
        }
        return getCountryId().equals(country.getCountryId()) && Objects.equals(getLibelle(), country.getLibelle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryId(), getLibelle());
    }
}
