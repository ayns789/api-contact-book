package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CivilityDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.EmailDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Civility;
import com.project.domain.entities.Contact;
import com.project.exceptions.ContactNotSavedException;
import com.project.exceptions.RessourcesNotFoundException;
import com.project.repository.ContactRepository;
import com.project.service.ContactService;
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

    /**
     * Retrieves a contact by its ID.
     *
     * @param id The ID of the contact.
     * @return The {@link ContactDTO} object representing the retrieved contact.
     */
    public ContactDTO getContact(Long id) {

        Contact contact = contactRepository.findById(id)
            .orElseThrow(RessourcesNotFoundException::new);

        // Save contactDTO
        return toDto(contact);
    }

    public List<ContactDTO> getContact(String lastName) {

        List<Contact> contacts = contactRepository.getContact(lastName)
            .orElseThrow(RessourcesNotFoundException::new);

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


