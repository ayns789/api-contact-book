package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CivilityDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.EmailDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.*;
import com.project.exceptions.*;
import com.project.repository.ContactRepository;
import com.project.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final EmailServiceImpl emailService;
    private final PhoneServiceImpl phoneService;
    private final CivilityServiceImpl civilityService;
    private final AddressServiceImpl addressService;

    @Override
    @Transactional
    public ContactDTO create(ContactDTO contactDTO) {

        // Handle civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // Handle contact
        Contact contact = save(contactDTO, civility);

        // Build contactDTO
        return toDto(contact);
    }

    @Override
    @Transactional
    public ContactDTO update(Long contactId, ContactDTO contactDTO){

        // check and get contact, if contact exist
        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(ContactNotFoundException::new);

        // get civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // update contact
        if (civility != null){
         existingContact.setCivility(civility);
        }

        if(contactDTO.getFirstName() != null && !contactDTO.getFirstName().isEmpty()) {
            if(!existingContact.getFirstName().equals(contactDTO.getFirstName())) {
                existingContact.setFirstName(contactDTO.getFirstName());
            }
        }

        if(contactDTO.getLastName() != null && !contactDTO.getLastName().isEmpty()) {
            if(!existingContact.getLastName().equals(contactDTO.getLastName())) {
                existingContact.setLastName(contactDTO.getLastName());
            }
        }

        // update emails
        if (contactDTO.getEmails() != null && !contactDTO.getEmails().isEmpty()) {

            List<Email> oldEmails = existingContact.getEmails();
            List<EmailDTO> newEmailDTOs = contactDTO.getEmails();
            List<Email> emailsUpdated =  emailService.updateEmail(oldEmails, newEmailDTOs);

            // save email list updated
            try {
                existingContact.setEmails(emailsUpdated);
            } catch (Exception e) {
                throw new EmailNotSavedException();
            }
        }

        // update phones
        if (contactDTO.getPhones() != null && !contactDTO.getPhones().isEmpty()) {

            List<Phone> oldPhones = existingContact.getPhones();
            List<PhoneDTO> newPhoneDTOs = contactDTO.getPhones();
            List<Phone> phonesUpdated =  phoneService.updatePhone(oldPhones, newPhoneDTOs);

            // save phone list updated
            try {
                existingContact.setPhones(phonesUpdated);
            } catch (Exception e) {
                throw new PhoneNotSavedException();
            }
        }

        if(contactDTO.getAddresses() != null && !contactDTO.getAddresses().isEmpty()) {
            List<Address> oldAddresses = existingContact.getAddresses();
            List<AddressDTO> newAddressDTOs = contactDTO.getAddresses();
            List<Address> addressUpdated = addressService.updateAddress(oldAddresses, newAddressDTOs);

            try {
                existingContact.setAddresses(addressUpdated);
            } catch (Exception e) {
                throw new AddressNotSavedException();
            }
        }

        // save contact with new datas
        try {
            Contact updatedContact = contactRepository.save(existingContact);
            ContactDTO responseDTO = toDto(updatedContact);
            responseDTO.setContactId(null);
            return responseDTO;
        } catch (Exception e) {
            log.error("Error during contact update: {}", e.getMessage(), e);
            throw new ContactNotUpdatedException();
        }
    }


    /**
     * Retrieves a contact by its ID.
     *
     * @param id The ID of the contact.
     * @return The {@link ContactDTO} object representing the retrieved contact.
     */
    public ContactDTO getContact(Long id) {

        Contact contact = contactRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);

        // Save contactDTO
        return toDto(contact);
    }

    public List<ContactDTO> getContactByLastname(String lastName) {

        List<Contact> contacts = contactRepository.getContactByLastname(lastName)
            .orElseThrow(LastnameNotFoundException::new);

        return toDto(contacts);
    }

    public List<ContactDTO> getContactByFirstname(String firstName) {

        List<Contact> contacts = contactRepository.getContactByFirstname(firstName)
                .orElseThrow(FirstnameNotFoundException::new);

        return toDto(contacts);
    }

    public List<ContactDTO> getContactByPhone(String phoneNumber) {

        List<Contact> contacts = contactRepository.getContactByPhone(phoneNumber)
                .orElseThrow(PhoneNotFoundException::new);

        return toDto(contacts);
    }


    @Override
    public Contact save(ContactDTO contactDTO, Civility civility) {

        Contact contact = Contact.builder()
            .firstName(contactDTO.getFirstName())
            .lastName(contactDTO.getLastName())
            .civility(civility)
            .build();

        // Save contact
        try {
            return contactRepository.save(contact);
        } catch (Exception e) {

            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
            throw new ContactNotSavedException();
        }
    }

    @Override
    public ContactDTO toDto(Contact contact) {

        CivilityDTO civilityDTO = civilityService.toDto(contact.getCivility());

        List<EmailDTO> emailDTOs = emailService.toDto(contact.getEmails());
        emailDTOs = ListUtils.emptyIfNull(emailDTOs);

        List<PhoneDTO> phoneDTOs = phoneService.toDto(contact.getPhones());
        phoneDTOs = ListUtils.emptyIfNull(phoneDTOs);

        List<AddressDTO> addressDTOs = addressService.toDto(contact.getAddresses());
        addressDTOs = ListUtils.emptyIfNull(addressDTOs);

        return ContactDTO.builder()
            .contactId(contact.getContactId())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .civility(civilityDTO)
            .emails(emailDTOs)
            .phones(phoneDTOs)
            .addresses(addressDTOs)
            .build();
    }

    @Override
    public List<ContactDTO> toDto(List<Contact> contacts) {
        return contacts.stream()
            .map(this::toDto)
            .toList();
    }
}


