package com.project.carnet.service;

import com.project.carnet.dto.ContactCreateDTO;
import com.project.carnet.dto.ContactDTO;
import com.project.carnet.entities.Contact;
import com.project.carnet.repository.ContactRepository;
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
