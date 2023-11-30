package com.project.service;

import com.project.dto.*;
import com.project.entities.*;
import com.project.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final CivilityRepository civilityRepository;

    private final EmailRepository emailRepository;

    private final PhoneRepository phoneRepository;

    private final AddressRepository addressRepository;

    private final CountryRepository countryRepository;

    public ContactServiceImpl(ContactRepository contactRepository, CivilityRepository civilityRepository, EmailRepository emailRepository, PhoneRepository phoneRepository, AddressRepository addressRepository, CountryRepository countryRepository) {
        this.contactRepository = contactRepository;
        this.civilityRepository = civilityRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
    }


    public List<Phone> getPhonesToDTO(ContactDTO contactDTO, Contact contact) {

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

    public List<Email> getEmailsToDTO(ContactDTO contactDTO, Contact contact) {

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

    public List<Address> getAddressesToDTO(ContactDTO contactDTO, Contact contact) {

        List<Address> addresses = new ArrayList<>();
        contactDTO.getAddresses().forEach(addressDTO -> {

            Long countryId = addressDTO.getCountry().getCountryId();
            Country country = countryRepository.getReferenceById(countryId);

            Address address = new Address();
            address.setCountry(country);
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

    public List<PhoneDTO> phonesUpdateDTO(List<Phone> phonesGetRepo) {
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phonesGetRepo.forEach(phone -> {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setPhoneId(phone.getPhoneId());
            phoneDTO.setLibelle(phone.getLibelle());
            phoneDTO.setType(phone.getType());
            phonesDTO.add(phoneDTO);
        });
        return phonesDTO;
    }


    public List<EmailDTO> emailUpdateDTO(List<Email> emailsGetRepo) {
        List<EmailDTO> emailsDTO = new ArrayList<>();
        emailsGetRepo.forEach(email -> {

            EmailDTO emailDTO = new EmailDTO();

            emailDTO.setEmailId(email.getEmailId());
            emailDTO.setLibelle(email.getLibelle());
            emailDTO.setType(email.getType());

            emailsDTO.add(emailDTO);
        });

        return emailsDTO;
    }

    public List<AddressDTO> addressesUpdateDTO(List<Address> addressesGetRepo) {
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addressesGetRepo.forEach(address -> {

            CountryDTO countryDTO = new CountryDTO();
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setAddressId(address.getAddressId());
            addressDTO.setStreetNumber(address.getStreetNumber());
            addressDTO.setStreetType(address.getStreetType());
            addressDTO.setStreetName(address.getStreetName());
            addressDTO.setCityName(address.getCityName());
            addressDTO.setPostalCode(address.getPostalCode());

            countryDTO.setCountryId(address.getCountry().getCountryId());
            countryDTO.setLibelle(address.getCountry().getLibelle());
            addressDTO.setCountry(countryDTO);

            addressesDTO.add(addressDTO);
        });
        return addressesDTO;
    }

    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityRepository.getReferenceById(civilityId);

        Contact contact = new Contact();
        contact.setCivility(civility);
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());

        List<Phone> phonesGetRepo = new ArrayList<>();
        List<Email> emailsGetRepo = new ArrayList<>();
        List<Address> addressesGetRepo = new ArrayList<>();

        contact = contactRepository.save(contact);
        emailsGetRepo = emailRepository.saveAll(getEmailsToDTO(contactDTO, contact));
        phonesGetRepo = phoneRepository.saveAll(getPhonesToDTO(contactDTO, contact));
        addressesGetRepo = addressRepository.saveAll(getAddressesToDTO(contactDTO, contact));

        contactDTO.setContactId(contact.getContactId());
        contactDTO.setEmails(emailUpdateDTO(emailsGetRepo));
        contactDTO.setPhones(phonesUpdateDTO(phonesGetRepo));
        contactDTO.setAddresses(addressesUpdateDTO(addressesGetRepo));

        return contactDTO;
    }


}


