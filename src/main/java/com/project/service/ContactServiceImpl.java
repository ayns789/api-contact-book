package com.project.service;

import com.project.dto.AddressDTO;
import com.project.dto.ContactDTO;
import com.project.dto.EmailDTO;
import com.project.dto.PhoneDTO;
import com.project.entities.*;
import com.project.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ContactDTO createContact(ContactDTO input) {
        Civility civility = civilityRepository.getReferenceById(input.getCivility().getCivilityId());
        Country country = countryRepository.getReferenceById(input.getAddresses().get(0).getCountry().getCountryId());

        Contact contact = new Contact();
        contact.setCivility(civility);
        contact.setFirstName(input.getFirstName());
        contact.setLastName(input.getLastName());

        List<Email> emails = new ArrayList<>();
        input.getEmails().forEach(emailInput -> {
            Email email = new Email();
            email.setContact(contact);
            email.setLibelle(emailInput.getLibelle());
            email.setType(emailInput.getType());
            emails.add(email);
        });

        List<Phone> phones = new ArrayList<>();
        input.getPhones().forEach(phoneInput -> {
            Phone phone = new Phone();
            phone.setContact(contact);
            phone.setLibelle(phoneInput.getLibelle());
            phone.setType(phoneInput.getType());
            phones.add(phone);
        });

        List<Address> addresses = new ArrayList<>();
        input.getAddresses().forEach(addressInput -> {
            Address address = new Address();
            address.setCountry(country);
            address.setContact(contact);
            address.setStreetNumber(addressInput.getStreetNumber());
            address.setStreetType(addressInput.getStreetType());
            address.setStreetName(addressInput.getStreetName());
            address.setCityName(addressInput.getCityName());
            address.setPostalCode(addressInput.getPostalCode());
            addresses.add(address);
        });

        contactRepository.save(contact);
        emailRepository.saveAll(emails);
        phoneRepository.saveAll(phones);
        addressRepository.saveAll(addresses);

        return input;
    }
}




