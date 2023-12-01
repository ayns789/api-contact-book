package com.project.service;

import com.project.dto.ContactDTO;
import com.project.entities.*;
import com.project.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final EmailServiceImpl emailService;

    private final PhoneServiceImpl phoneService;

    private final CivilityServiceImpl civilityService;

    private final AddressServiceImpl addressService;

    public ContactServiceImpl(ContactRepository contactRepository, EmailServiceImpl emailService, PhoneServiceImpl phoneService, AddressServiceImpl addressService, CivilityServiceImpl civilityService) {
        this.contactRepository = contactRepository;
        this.addressService = addressService;
        this.civilityService = civilityService;
        this.emailService = emailService;
        this.phoneService = phoneService;
    }


    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        Contact contact = new Contact();
        contact.setCivility(civility);
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());

        List<Phone> phones = new ArrayList<>();
        List<Email> emails = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();

        contact = contactRepository.save(contact);
        emails = emailService.saveEmails(contactDTO, contact);
        phones = phoneService.savePhones(contactDTO, contact);
        addresses = addressService.saveAddresses(contactDTO, contact);

        contactDTO.setContactId(contact.getContactId());
        contactDTO.setEmails(emailService.emailUpdateDTO(emails));
        contactDTO.setPhones(phoneService.phonesUpdateDTO(phones));
        contactDTO.setAddresses(addressService.addressesUpdateDTO(addresses));

        return contactDTO;
    }


}


