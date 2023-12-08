package com.project.service.implementation;

import com.project.dto.*;
import com.project.entities.*;
import com.project.repository.ContactRepository;
import com.project.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final EmailServiceImpl emailService;
    private final PhoneServiceImpl phoneService;
    private final CivilityServiceImpl civilityService;
    private final AddressServiceImpl addressService;

    @Override
    @Transactional
    public ContactDTO create(ContactDTO contactDTO) {

        try {
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

        } catch (Exception e) {
            // Handle IllegalArgumentException
            String errorMessage = "Invalid contact data";

            throw new Exception(errorMessage, e);
        }


    }


    @Override
    public Contact save(ContactDTO contactDTO, Civility civility) {
        Contact contact = Contact.builder()
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .civility(civility)
                .build();

        // Save contact
        contact = contactRepository.save(contact);
        return contact;
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
}


