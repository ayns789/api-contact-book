package com.project.entities;

import com.project.enums.CivilityEnumType;
import jakarta.persistence.*;

@Entity
@Table(name = "civility")
@SequenceGenerator(name = "CivilityIdGenerator", sequenceName = "civility_seq", allocationSize = 1)
public class Civility {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CivilityIdGenerator")
    @Column(name = "civility_id", nullable = false)
    private Long civilityId;

    @Column(name = "libelle")
    @Enumerated(EnumType.STRING)
    private CivilityEnumType libelle;


    public Long getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(Long civilityId) {
        this.civilityId = civilityId;
    }

    public CivilityEnumType getLibelle() {
        return libelle;
    }

    public void setLibelle(CivilityEnumType libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Civility{" +
                "civilityId=" + civilityId +
                ", libelle=" + libelle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Civility civility)) return false;

        if (civilityId != civility.civilityId) return false;
        return libelle == civility.libelle;
    }

    @Override
    public int hashCode() {
        int result = (int) (civilityId ^ (civilityId >>> 32));
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
