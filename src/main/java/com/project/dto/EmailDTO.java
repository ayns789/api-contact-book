package com.project.dto;

import com.project.enums.EmailTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;


public class EmailDTO {


    private Long emailId;

    @NotNull
    @NotBlank
    @Email
    @Size(max = 255, message
            = "'libelle' from email must be under 255 characters")
    private String libelle;
    
    @Enumerated(EnumType.STRING)
    private EmailTypeEnum type;

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public EmailTypeEnum getType() {
        return type;
    }

    public void setType(EmailTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "emailId=" + emailId +
                ", libelle='" + libelle + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailDTO emailDTO)) return false;

        if (emailId != emailDTO.emailId) return false;
        if (!Objects.equals(libelle, emailDTO.libelle)) return false;
        return type == emailDTO.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (emailId ^ (emailId >>> 32));
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
