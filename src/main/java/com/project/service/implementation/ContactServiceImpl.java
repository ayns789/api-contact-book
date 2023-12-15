package com.project.service.implementation;

import com.project.domain.dto.*;
import com.project.domain.entities.*;
import com.project.exceptions.ContactNotSavedException;
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
        CivilityDTO civilityDTO = civilityService.toDto(civility);

        // Handle contact
        Contact contact = save(contactDTO, civility);

        // Handle emails
        List<EmailDTO> emailDTOS = contactDTO.getEmails();
        emailDTOS = ListUtils.emptyIfNull(emailDTOS);
        List<Email> emails = emailService.save(emailDTOS, contact);
        List<EmailDTO> emailDTOs = emailService.toDto(emails);

        // Handle phones
        List<PhoneDTO> phoneDTOS = contactDTO.getPhones();
        List<Phone> phones = phoneService.save(phoneDTOS, contact);
        List<PhoneDTO> phoneDTOs = phoneService.toDto(phones);

        // Handle addresses
        List<AddressDTO> addressDTOS = contactDTO.getAddresses();
        addressDTOS = ListUtils.emptyIfNull(addressDTOS);
        List<Address> addresses = addressService.save(addressDTOS, contact);
        List<AddressDTO> addressDTOs = addressService.toDto(addresses);

        // Build contactDTO
        return toDto(contact, civilityDTO, emailDTOs, phoneDTOs, addressDTOs);
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

            log.error(STR. "Error during contact creation: \{ e.getMessage() }" , e);
            throw new ContactNotSavedException();
        }
    }

    @Override
    public ContactDTO toDto(Contact contact, CivilityDTO civilityDTO, List<EmailDTO> emailDTOS,
                            List<PhoneDTO> phoneDTOS, List<AddressDTO> addressDTOS) {

        return ContactDTO.builder()
                .contactId(contact.getContactId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .civility(civilityDTO)
                .emails(emailDTOS)
                .phones(phoneDTOS)
                .addresses(addressDTOS)
                .build();
    }

    public ContactDTO getByID(Long id) {

        return null;
    }

    public ContactDTO getByFirstName(String firstName) {

        return null;
    }

    public ContactDTO getByLastName(String lastName) {

        return null;
    }

    public ContactDTO getByPhoneNumber(String libelle) {

        return null;
    }
}


