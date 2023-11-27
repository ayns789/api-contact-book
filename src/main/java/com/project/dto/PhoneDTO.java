package com.project.dto;

import com.project.entities.Contact;
import com.project.enums.PhoneTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PhoneDTO {

    private Long contactId;

    @NotNull
    @NotBlank
    @Max(value = 50, message
            = "'libelle' from phone must be under 50 characters")
    private String libelle;

    @NotNull
    @NotBlank
    @Max(value=20, message = "'type' of phone must be under 20 characters")
    @Enumerated(EnumType.STRING)
    private PhoneTypeEnum phoneType;

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

    public PhoneTypeEnum getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneTypeEnum phoneType) {
        this.phoneType = phoneType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
