package com.project.dto;

import com.project.enums.CivilityEnumType;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class CivilityDTO {
    private long civilityId;

    @NotNull
    @NotBlank
    @Size(max=20, message = "'libelle' of civility must be under 20 characters")
    private CivilityEnumType libelle;


    public long getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(long civilityId) {
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
        return "CivilityDTO{" +
                "civilityId=" + civilityId +
                ", libelle=" + libelle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CivilityDTO that)) return false;

        if (civilityId != that.civilityId) return false;
        return libelle == that.libelle;
    }

    @Override
    public int hashCode() {
        int result = (int) (civilityId ^ (civilityId >>> 32));
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }
}
