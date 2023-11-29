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
import java.util.stream.Collectors;

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


        public List<Phone> getPhones(ContactDTO contactDTO, Contact contact) {

            List<Phone> phones = new ArrayList<>();
            contactDTO.getPhones().forEach(phoneDTO -> {
                Phone phone = new Phone();
                phone.setContact(contact);
                phone.setLibelle(phoneDTO.getLibelle());
                phone.setType(phoneDTO.getType());
                phones.add(phone);
            });

            return phones;
        }

        public List<Email> getEmails(ContactDTO contactDTO, Contact contact) {

            List<Email> emails = new ArrayList<>();
            contactDTO.getEmails().forEach(emailDTO -> {
                Email email = new Email();
                email.setContact(contact);
                email.setLibelle(emailDTO.getLibelle());
                email.setType(emailDTO.getType());
                emails.add(email);
            });

            return emails;
        }

    public List<Address> getAddresses(ContactDTO contactDTO, Contact contact) {

        List<Address> addresses = new ArrayList<>();
        contactDTO.getAddresses().forEach(addressDTO -> {
            Address address = new Address();
            address.setCountry(countryRepository.getReferenceById(addressDTO.getCountry().getCountryId()));
            address.setContact(contact);
            address.setStreetNumber(addressDTO.getStreetNumber());
            address.setStreetType(addressDTO.getStreetType());
            address.setStreetName(addressDTO.getStreetName());
            address.setCityName(addressDTO.getCityName());
            address.setPostalCode(addressDTO.getPostalCode());
            addresses.add(address);
        });

        return addresses;
    }



    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        Civility civility = civilityRepository.getReferenceById(contactDTO.getCivility().getCivilityId());

        Contact contact = new Contact();
        contact.setCivility(civility);
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());

        contactRepository.save(contact);
        emailRepository.saveAll(getEmails(contactDTO, contact));
        phoneRepository.saveAll(getPhones(contactDTO, contact));
        addressRepository.saveAll(getAddresses(contactDTO, contact));

        return contactDTO;
    }


}
