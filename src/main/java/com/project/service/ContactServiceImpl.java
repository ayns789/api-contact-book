package com.project.service;

import com.project.dto.ContactDTO;
import com.project.entities.Civility;
import com.project.entities.Contact;
import com.project.repository.CivilityRepository;
import com.project.repository.ContactRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements  ContactService {

    private final ContactRepository contactRepository;

    private final CivilityRepository civilityRepository;

    protected ContactServiceImpl(ContactRepository contactRepository, CivilityRepository civilityRepository) {
        this.contactRepository = contactRepository;
        this.civilityRepository = civilityRepository;
    }
    @Override
    public ContactDTO createContact(ContactDTO dto) {
        System.out.println(" le dto recu est :  " + dto);
        Civility civility = civilityRepository.getReferenceById(dto.getCivility().getCivilityId());
        System.out.println("dto.getCivility().getCivilityId() : " + dto.getCivility().getCivilityId());
        System.out.println("civility : " + civility);

        Contact contact = new Contact();
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
//        contact.setEmails(dto.getEmails());
        contact.setCivility(civility);
//        contact.setPhones(dto.getPhones());
//        contact.setAddresses(dto.getAddresses());

        System.out.println(" le contact recu est :  " + contact);
        contactRepository.save(contact);
        return dto;
    }
}
