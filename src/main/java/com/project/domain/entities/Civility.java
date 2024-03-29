package com.project.domain.entities;

import com.project.domain.enums.CivilityEnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "civility")
@SequenceGenerator(name = "CivilityIdGenerator", sequenceName = "civility_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Civility implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CivilityIdGenerator")
    @Column(name = "civility_id", nullable = false)
    private Long civilityId;

    @Column(name = "libelle")
    @Enumerated(EnumType.STRING)
    private CivilityEnumType libelle;

    @Override
    public String toString() {
        return STR."Civility{civilityId=\{civilityId}, libelle=\{libelle}\{'}'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Civility civility)) {
            return false;
        }

        if (!civilityId.equals(civility.civilityId)) {
            return false;
        }
        return libelle == civility.libelle;
    }

    @Override
    public int hashCode() {
        int result = (int) (civilityId ^ (civilityId >>> 32));
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
