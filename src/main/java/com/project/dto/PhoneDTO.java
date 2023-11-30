package com.project.dto;

import com.project.enums.PhoneTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;


public class PhoneDTO {

    private Long phoneId;

    @NotNull
    @NotBlank
    @Size(max = 50, message
            = "'libelle' from phone must be under 50 characters")
    private String libelle;

    @NotNull
    @NotBlank
    @Size(max = 20, message = "'type' of phone must be under 20 characters")
    @Enumerated(EnumType.STRING)
    private PhoneTypeEnum type;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public PhoneTypeEnum getType() {
        return type;
    }

    public void setType(PhoneTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "phoneId=" + phoneId +
                ", libelle='" + libelle + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneDTO phoneDTO)) return false;

        if (!Objects.equals(phoneId, phoneDTO.phoneId)) return false;
        if (!Objects.equals(libelle, phoneDTO.libelle)) return false;
        return type == phoneDTO.type;
    }

    @Override
    public int hashCode() {
        int result = phoneId != null ? phoneId.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
