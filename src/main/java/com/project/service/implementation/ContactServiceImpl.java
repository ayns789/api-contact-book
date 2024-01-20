package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CivilityDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.EmailDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Civility;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Email;
import com.project.domain.entities.Phone;
import com.project.exceptions.ContactAlreadyExistException;
import com.project.exceptions.ContactNotDeletedException;
import com.project.exceptions.ContactNotFoundException;
import com.project.exceptions.ContactNotSavedException;
import com.project.exceptions.FirstnameNotFoundException;
import com.project.exceptions.IdNotFoundException;
import com.project.exceptions.LastnameNotFoundException;
import com.project.exceptions.PhoneNotFoundException;
import com.project.repository.ContactRepository;
import com.project.service.AddressService;
import com.project.service.CivilityService;
import com.project.service.ContactService;
import com.project.service.EmailService;
import com.project.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;
    private final PhoneService phoneService;
    private final CivilityService civilityService;
    private final AddressService addressService;

    @Override
    @Transactional
    public ContactDTO create(ContactDTO contactDTO) {

        // check if contact already exist in database
        Boolean contactAlreadyExist = contactRepository.existsByLastNameAndAndFirstName(contactDTO.getLastName(), contactDTO.getFirstName());

        if (contactAlreadyExist) {
            throw new ContactAlreadyExistException();
        }

        // Handle civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // Handle contact
        Contact contact = save(contactDTO, civility);

        // Build contactDTO
        return toDto(contact);
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

    /**
     * Get all contacts in database.
     *
     * @return The {@link Contact} object representing the contact deleted.
     */
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    /**
     * Retrieves contacts by last name.
     *
     * @param lastName The lastName of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    public List<ContactDTO> getContactByLastname(String lastName) {

        List<Contact> contacts = contactRepository.getContactByLastname(lastName)
            .orElseThrow(LastnameNotFoundException::new);

        return toDto(contacts);
    }

    /**
     * Retrieves contacts by first name.
     *
     * @param firstName The firstName of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    public List<ContactDTO> getContactByFirstname(String firstName) {

        List<Contact> contacts = contactRepository.getContactByFirstname(firstName)
            .orElseThrow(FirstnameNotFoundException::new);

        return toDto(contacts);
    }

    /**
     * Retrieves contacts by phone numbers.
     *
     * @param phoneNumber The phoneNumber of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    public List<ContactDTO> getContactByPhone(String phoneNumber) {

        List<Contact> contacts = contactRepository.getContactByPhone(phoneNumber)
            .orElseThrow(PhoneNotFoundException::new);

        return toDto(contacts);
    }

    @Override
    public Contact save(ContactDTO contactDTO, Civility civility) {

        // edit contact
        Contact contact = buildContact(contactDTO, civility);

        // handle emails
        List<EmailDTO> emailDTOs = contactDTO.getEmails();
        emailDTOs = ListUtils.emptyIfNull(emailDTOs);
        List<Email> emails = emailService.save(emailDTOs, contact);

        // handle phones
        List<PhoneDTO> phoneDTOs = contactDTO.getPhones();
        phoneDTOs = ListUtils.emptyIfNull(phoneDTOs);
        List<Phone> phones = phoneService.save(phoneDTOs, contact);

        // handle addresses
        List<AddressDTO> addressDTOs = contactDTO.getAddresses();
        addressDTOs = ListUtils.emptyIfNull(addressDTOs);
        List<Address> addresses = addressService.save(addressDTOs, contact);

        // set emails, phones and addresses
        buildContact(contact, emails, phones, addresses);

        // Save contact
        try {
            return contactRepository.save(contact);
        } catch (Exception e) {

            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
            throw new ContactNotSavedException();
        }
    }

    private void buildContact(Contact contact, List<Email> emails, List<Phone> phones, List<Address> addresses) {
        contact.setEmails(emails);
        contact.setPhones(phones);
        contact.setAddresses(addresses);
    }

    private Contact buildContact(ContactDTO contactDTO, Civility civility) {
        return Contact.builder()
            .contactId(contactDTO.getContactId())
            .firstName(contactDTO.getFirstName())
            .lastName(contactDTO.getLastName())
            .civility(civility)
            .build();
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

    public Contact toEntity(ContactDTO contactDTO) {
        Civility civility = civilityService.toEntity(contactDTO.getCivility());

        List<Email> emails = emailService.toEntity(contactDTO.getEmails());
        emails = ListUtils.emptyIfNull(emails);

        List<Phone> phones = phoneService.toEntity(contactDTO.getPhones());
        phones = ListUtils.emptyIfNull(phones);

        List<Address> addresses = addressService.toEntity(contactDTO.getAddresses());
        addresses = ListUtils.emptyIfNull(addresses);

        return Contact.builder()
            .contactId(contactDTO.getContactId())
            .firstName(contactDTO.getFirstName())
            .lastName(contactDTO.getLastName())
            .civility(civility)
            .emails(emails)
            .phones(phones)
            .addresses(addresses)
            .build();
    }

    /**
     * Edit a contact by their ID.
     *
     * @param contactId  The id of the contact.
     * @param contactDTO The data of the contact to edit.
     * @return The {@link ContactDTO} object representing the contact edited.
     */
    @Override
    @Transactional
    public ContactDTO update(Long contactId, ContactDTO contactDTO) {

        // Check if contact exist in bdd
        boolean existsById = contactRepository.existsById(contactId);

        if (!existsById) {
            log.error("Contact with id {} not found", contactId);
            throw new ContactNotFoundException();
        }

        // Handle civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // Handle contact
        contactDTO.setContactId(contactId);
        Contact contact = buildContact(contactDTO, civility);

        // handle emails
        List<Email> emails = emailService.updateEmails(contactDTO, contact);

        // handle phones
        List<Phone> phones = phoneService.updatePhones(contactDTO, contact);

        // handle addresses
        List<Address> addresses = addressService.updateAddresses(contactDTO, contact);

        // set emails, phones and addresses
        buildContact(contact, emails, phones, addresses);

        // Save contact
        try {
            contact = contactRepository.save(contact);
        } catch (Exception e) {

            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
            throw new ContactNotSavedException();
        }

        // Build contactDTO
        return toDto(contact);
    }

    /**
     * Delete a contact by its ID.
     *
     * @param id The id of the contact.
     * @return The {@link ContactDTO} object representing the contact deleted.
     */
    @Override
    @Transactional
    public ContactDTO delete(Long id) {

        ContactDTO contactDTO = getContact(id);
        Contact contact = toEntity(contactDTO);

        // delete emails
        if (contact.getEmails() != null && !contact.getEmails().isEmpty()) {
            List<Email> emails = contact.getEmails();
            emailService.deleteAll(emails);
        }

        // delete phones
        List<Phone> phones = contact.getPhones();
        phoneService.deleteAll(phones);

        // delete addresses
        if (contact.getAddresses() != null && !contact.getAddresses().isEmpty()) {
            List<Address> addresses = contact.getAddresses();
            addressService.deleteAll(addresses);
        }

        // delete contact
        contactRepository.deleteById(contact.getContactId());

        try {
            return toDto(contact);
        } catch (Exception e) {
            log.error(STR."Error during contact deleted operation: \{e.getMessage()}", e);
            throw new ContactNotDeletedException();
        }
    }
}


