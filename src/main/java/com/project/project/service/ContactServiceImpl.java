package com.project.project.service;

import com.project.project.dto.ContactCreateDTO;
import com.project.project.entities.Contact;
import com.project.project.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements  ContactService {

    private final ContactRepository contactRepository;

    protected ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    @Override
    public void createContact(ContactCreateDTO dto) {
        Contact contact = new Contact(dto.getFirstName(), dto.getLastName(), dto.getCivility(), dto.getEmails(), dto.getAddresses(), dto.getPhones());

        contactRepository.save(contact);
    }
}
