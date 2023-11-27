package com.project.dto;

import com.project.entities.Contact;
import com.project.enums.PhoneTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class PhoneDTO {

    private Long contactId;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "libelle of phone must be only numbers")
    @Max(value = 50, message
            = "'libelle' from phone must be under 50 characters")
    private String libelle;

    @NotNull
    @NotBlank
    @Max(value=20, message = "'type' of phone must be under 20 characters")
    @Enumerated(EnumType.STRING)
    private PhoneTypeEnum type;

    private Contact contact;

    public PhoneDTO(){

    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
