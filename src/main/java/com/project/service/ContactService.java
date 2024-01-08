package com.project.service;


import com.project.domain.dto.ContactDTO;
import com.project.domain.entities.Civility;
import com.project.domain.entities.Contact;

import java.io.IOException;
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

    /**
     * Retrieves contacts by last name.
     *
     * @param lastName The lastName of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    List<ContactDTO> getContactByLastname(String lastName);

    /**
     * Retrieves contacts by first name.
     *
     * @param firstName The firstName of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    List<ContactDTO> getContactByFirstname(String firstName);

    /**
     * Retrieves contacts by phone numbers.
     *
     * @param phoneNumber The phoneNumber of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    List<ContactDTO> getContactByPhone(String phoneNumber);

    /**
     * Edit a contact by their ID.
     *
     * @param contactId  The id of the contact.
     * @param contactDTO The data of the contact to edit.
     * @return The {@link ContactDTO} object representing the contact edited.
     */
    ContactDTO update(Long contactId, ContactDTO contactDTO);

    /**
     * Delete a contact by its ID.
     *
     * @param id The id of the contact.
     * @return The {@link ContactDTO} object representing the contact deleted.
     */
    ContactDTO delete(Long id);

    /**
     * Export an Excel file representing the contacts, on a file path.
     */
    void exportFile() throws IOException;

}
