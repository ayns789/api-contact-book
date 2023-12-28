package com.project.service;


import com.project.domain.dto.ContactDTO;
import com.project.domain.entities.Civility;
import com.project.domain.entities.Contact;

import java.util.List;

public interface ContactService {

    ContactDTO create(ContactDTO contactDTO);

    Contact save(ContactDTO contactDTO, Civility civility);

    ContactDTO toDto(Contact contact);

    List<ContactDTO> toDto(List<Contact> contacts);

    /**
     * Retrieves a contact by its ID.
     *
     * @param id The ID of the contact.
     * @return The {@link ContactDTO} object representing the retrieved contact.
     */
    ContactDTO getContact(Long id);

    List<ContactDTO> getContactByLastname(String lastName);

    List<ContactDTO> getContactByFirstname(String firstName);

    List<ContactDTO> getContactByPhone(String phoneNumber);
}
