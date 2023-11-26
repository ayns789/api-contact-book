package com.project.service;

import com.project.dto.ContactDTO;
import com.project.entities.*;
import com.project.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements  ContactService {

    private final ContactRepository contactRepository;

    private final CivilityRepository civilityRepository;

    private final EmailRepository emailRepository;

    private final PhoneRepository phoneRepository;

    private final AddressRepository addressRepository;

    private final CountryRepository countryRepository;

    protected ContactServiceImpl(ContactRepository contactRepository, CivilityRepository civilityRepository, EmailRepository emailRepository, PhoneRepository phoneRepository, AddressRepository addressRepository, CountryRepository countryRepository) {
        this.contactRepository = contactRepository;
        this.civilityRepository = civilityRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
    }
    @Override
    public ContactDTO createContact(ContactDTO dto) {
        Civility civility = civilityRepository.getReferenceById(dto.getCivility().getCivilityId());
        Country country = countryRepository.getReferenceById(dto.getAddresses().get(0).getCountry().getCountryId());

        Contact contact = new Contact();
        contact.setCivility(civility);
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());

        Email email = new Email();
        email.setContact(contact);
        email.setLibelle(dto.getEmails().get(0).getLibelle());
        email.setType(dto.getEmails().get(0).getType());

        Phone phone = new Phone();
        phone.setContact(contact);
        phone.setLibelle(dto.getPhones().get(0).getLibelle());
        phone.setType(dto.getPhones().get(0).getType());

        Address address = new Address();
        address.setCountry(country);
        address.setContact(contact);
        address.setStreetNumber(dto.getAddresses().get(0).getStreetNumber());
        address.setStreetType(dto.getAddresses().get(0).getStreetType());
        address.setStreetName(dto.getAddresses().get(0).getStreetName());
        address.setCityName(dto.getAddresses().get(0).getCityName());
        address.setPostalCode(dto.getAddresses().get(0).getPostalCode());

        contactRepository.save(contact);
        emailRepository.save(email);
        phoneRepository.save(phone);
        addressRepository.save(address);

        return dto;
    }
}
