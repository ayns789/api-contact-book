package com.project.carnet.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "civility")
@SequenceGenerator(name = "civility.seq_civility", sequenceName = "seq_civility", allocationSize = 1)
public class Civility {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "civility.seq_civility")
    @Column(name = "civility_id", nullable = false)
    private long civilityId;

    @Column(name = "libelle")
    private String libelle;

    protected Civility() {

    }

    public long getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(long civilityId) {
        this.civilityId = civilityId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Civility{" +
                "civilityId=" + civilityId +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Civility civility)) return false;
        return getCivilityId() == civility.getCivilityId() && Objects.equals(getLibelle(), civility.getLibelle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCivilityId(), getLibelle());
    }
}
