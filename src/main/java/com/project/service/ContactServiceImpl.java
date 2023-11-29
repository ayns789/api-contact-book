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

//    public List<Phone> getPhones(ContactDTO input, Contact contact) {
//
//        List<PhoneDTO> phonesDTO = input.getPhones().stream()
//                .map(phoneData -> new PhoneDTO(contact, phoneData.getLibelle(), phoneData.getType()))
//                .toList();
//
//        List<Phone> phones = phonesDTO.stream()
//                .map(phoneDTO -> new Phone(phoneDTO.getContact(), phoneDTO.getLibelle(), phoneDTO.getType()))
//                .collect(Collectors.toList());
//
//            return phones;
//    }
//
//    public List<Email> getEmails(ContactDTO input, Contact contact) {
//
//        List<EmailDTO> emailsDTO = input.getEmails().stream()
//                .map(emailData -> new EmailDTO(contact, emailData.getLibelle(), emailData.getType()))
//                .toList();
//
//        List<Email> emails =  emailsDTO.stream()
//                .map(emailDTO -> new Email(emailDTO.getContact(), emailDTO.getLibelle(), emailDTO.getType()))
//                .collect(Collectors.toList());
//
//        return emails;
//    }
//
//    public List<Address> getAddresses(ContactDTO input, Contact contact) {
//        List<AddressDTO> addressesDTO = input.getAddresses().stream()
//                .map(addressData -> new AddressDTO(countryRepository.getReferenceById(addressData.getCountry().getCountryId()), contact, addressData.getStreetNumber(), addressData.getStreetType(), addressData.getStreetName(),
//                        addressData.getCityName(), addressData.getPostalCode()))
//                .toList();
//
//        List<Address> addresses = addressesDTO.stream()
//                .map(addressDto -> new Address(addressDto.getCountry(),addressDto.getContact(), addressDto.getStreetNumber(), addressDto.getStreetType(), addressDto.getStreetName(),
//                        addressDto.getCityName(), addressDto.getPostalCode()))
//                .toList();
//
//          return addresses;
//    }

    public List<Email> getEmails(ContactDTO input, Contact contact) {

        List<EmailDTO> emailsDTO = new ArrayList<>();
        input.getEmails().forEach(emailData -> {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setContact(contact);
            emailDTO.setLibelle(emailData.getLibelle());
            emailDTO.setType(emailData.getType());
            emailsDTO.add(emailDTO);
        });

        List<Email> emails = new ArrayList<>();
        emailsDTO.forEach(emailDTO -> {
            Email email = new Email();
            email.setContact(emailDTO.getContact());
            email.setLibelle(emailDTO.getLibelle());
            email.setType(emailDTO.getType());
            emails.add(email);
        });

        return emails;
    }

    public List<Phone> getPhones(ContactDTO input, Contact contact) {

        List<PhoneDTO> phonesDTO = new ArrayList<>();
        input.getPhones().forEach(phoneData -> {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setContact(contact);
            phoneDTO.setLibelle(phoneData.getLibelle());
            phoneDTO.setType(phoneData.getType());
            phonesDTO.add(phoneDTO);
        });

        List<Phone> phones = new ArrayList<>();
        phonesDTO.forEach(phoneDTO -> {
            Phone phone = new Phone();
            phone.setContact(phoneDTO.getContact());
            phone.setLibelle(phoneDTO.getLibelle());
            phone.setType(phoneDTO.getType());
            phones.add(phone);
        });

        return phones;
    }

        public List<Address> getAddresses(ContactDTO input, Contact contact) {

            List<AddressDTO> addressesDTO = new ArrayList<>();
            input.getAddresses().forEach(addressData -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setCountry(countryRepository.getReferenceById(addressData.getCountry().getCountryId()));
                addressDTO.setContact(contact);
                addressDTO.setStreetNumber(addressData.getStreetNumber());
                addressDTO.setStreetType(addressData.getStreetType());
                addressDTO.setStreetName(addressData.getStreetName());
                addressDTO.setCityName(addressData.getCityName());
                addressDTO.setPostalCode(addressData.getPostalCode());
                addressesDTO.add(addressDTO);

            });

            List<Address> addresses = new ArrayList<>();
            addressesDTO.forEach(addressDTO -> {
                Address address = new Address();
                address.setCountry(addressDTO.getCountry());
                address.setContact(addressDTO.getContact());
                address.setStreetNumber(addressDTO.getStreetNumber());
                address.setStreetType(addressDTO.getStreetType());
                address.setStreetName(addressDTO.getStreetName());
                address.setCityName(addressDTO.getCityName());
                address.setPostalCode(addressDTO.getPostalCode());
                addresses.add(address);
            });

            return addresses;
        }

//        public List<Phone> getPhones(ContactDTO input, Contact contact) {
//
//            List<Phone> phones = new ArrayList<>();
//            input.getPhones().forEach(phoneData -> {
//                Phone phone = new Phone();
//                phone.setContact(contact);
//                phone.setLibelle(phoneData.getLibelle());
//                phone.setType(phoneData.getType());
//                phones.add(phone);
//            });
//
//            return phones;
//        }

//        public List<Email> getEmails(ContactDTO input, Contact contact) {
//
//            List<Email> emails = new ArrayList<>();
//            input.getEmails().forEach(emailData -> {
//                Email email = new Email();
//                email.setContact(contact);
//                email.setLibelle(emailData.getLibelle());
//                email.setType(emailData.getType());
//                emails.add(email);
//            });
//
//            return emails;
//        }

//    public List<Address> getAddresses(ContactDTO input, Contact contact) {
//
//        List<Address> addresses = new ArrayList<>();
//        input.getAddresses().forEach(addressData -> {
//            Address address = new Address();
//            address.setCountry(countryRepository.getReferenceById(addressData.getCountry().getCountryId()));
//            address.setContact(contact);
//            address.setStreetNumber(addressData.getStreetNumber());
//            address.setStreetType(addressData.getStreetType());
//            address.setStreetName(addressData.getStreetName());
//            address.setCityName(addressData.getCityName());
//            address.setPostalCode(addressData.getPostalCode());
//            addresses.add(address);
//        });
//
//        return addresses;
//    }



    @Override
    public ContactDTO createContact(ContactDTO input) {
        Civility civility = civilityRepository.getReferenceById(input.getCivility().getCivilityId());

        Contact contact = new Contact();
        contact.setCivility(civility);
        contact.setFirstName(input.getFirstName());
        contact.setLastName(input.getLastName());

        contactRepository.save(contact);
        emailRepository.saveAll(getEmails(input, contact));
        phoneRepository.saveAll(getPhones(input, contact));
        addressRepository.saveAll(getAddresses(input, contact));

        return input;
    }


}
