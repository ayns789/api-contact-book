package com.project.service;

import com.project.dto.ContactDTO;
import com.project.entities.Contact;
import com.project.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements  ContactService {

    private final ContactRepository contactRepository;

    protected ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    @Override
    public ContactDTO createContact(ContactDTO dto) {
        Contact contact = new Contact(dto.getFirstName(), dto.getLastName(), dto.getCivility(), dto.getEmails(), dto.getAddresses(), dto.getPhones());

        contactRepository.save(contact);
        return dto;
    }
}
