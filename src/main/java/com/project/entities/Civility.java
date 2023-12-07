package com.project.entities;

import com.project.enums.CivilityEnumType;
import jakarta.persistence.*;
import lombok.*;

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
        return "Civility{" +
                "civilityId=" + civilityId +
                ", libelle=" + libelle +
                '}';
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
