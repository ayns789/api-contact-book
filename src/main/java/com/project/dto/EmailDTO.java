package com.project.dto;

import com.project.entities.Contact;
import com.project.enums.EmailTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.util.Objects;


public class EmailDTO {


    private long emailId;

    @NotNull
    @NotBlank
    @Email
    @Size(max = 255, message
            = "'libelle' from email must be under 255 characters")
    private String libelle;

    @NotNull
    @NotBlank
    @Size(max=20, message = "'type' of email must be under 20 characters")
    @Enumerated(EnumType.STRING)
    private EmailTypeEnum type;

    public long getEmailId() {
        return emailId;
    }

    public void setEmailId(long emailId) {
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
