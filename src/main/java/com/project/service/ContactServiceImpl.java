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
        // get Civility by Id
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        Contact contact = new Contact();

        // contact get data from contactDTO
        contact.setCivility(civility);
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());

        List<Phone> phones = new ArrayList<>();
        List<Email> emails = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();

        // save all DTO datas in entities concerned
        try {
            contact = contactRepository.save(contact);
        } catch (Exception e) {
            System.out.println("Error in save process of contact" + e.getMessage());
        }

        emails = emailService.saveEmails(contactDTO, contact);
        phones = phoneService.savePhones(contactDTO, contact);
        addresses = addressService.saveAddresses(contactDTO, contact);


        // update contactDTO before return this ( with id from entities created )
        contactDTO.setContactId(contact.getContactId());
        contactDTO.setEmails(emailService.emailUpdateDTO(emails));
        contactDTO.setPhones(phoneService.phonesUpdateDTO(phones));
        contactDTO.setAddresses(addressService.addressesUpdateDTO(addresses));

        return contactDTO;
    }


}


