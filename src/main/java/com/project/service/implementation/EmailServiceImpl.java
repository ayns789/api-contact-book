package com.project.service.implementation;

import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.EmailDTO;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Email;
import com.project.domain.enums.EmailTypeEnum;
import com.project.exceptions.EmailNotDeletedException;
import com.project.exceptions.EmailNotSavedException;
import com.project.repository.EmailRepository;
import com.project.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    @Override
    public List<Email> save(List<EmailDTO> emailDTOS, Contact contact) {

        List<Email> emails = new ArrayList<>();

        emailDTOS.forEach(emailDTO -> {

            EmailTypeEnum emailTypeEnum = EmailTypeEnum.getValue(emailDTO.getType());

            Email email = Email.builder()
                    .emailId(emailDTO.getEmailId())
                    .libelle(emailDTO.getLibelle())
                    .type(emailTypeEnum)
                    .contact(contact)
                    .build();

            emails.add(email);
        });

        // save all emails
        try {
            return emailRepository.saveAll(emails);
        } catch (Exception e) {

            log.error("Error while saving emails: {}", e.getMessage(), e);
            throw new EmailNotSavedException();
        }
    }

    @Override
    public List<EmailDTO> toDto(List<Email> emails) {

        return emails.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public EmailDTO toDto(Email email) {

        return EmailDTO.builder()
                .emailId(email.getEmailId())
                .libelle(email.getLibelle())
                .type(email.getType().name())
                .build();
    }

    @Override
    public List<Email> toEntity(List<EmailDTO> emailDTOs) {

        return emailDTOs.stream()
                .map(emailDTO -> Email.builder()
                        .emailId(emailDTO.getEmailId())
                        .libelle(emailDTO.getLibelle())
                        .type(EmailTypeEnum.valueOf(emailDTO.getType()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll(List<Email> emails) {

        try {
            emailRepository.deleteAllInBatch(emails);
        } catch (Exception e) {
            log.error(STR."error message = \{e.getMessage()}, \{e}");
            throw new EmailNotDeletedException();
        }
    }

    @Override
    @Transactional
    public List<Email> updateEmails(ContactDTO contactDTO, Contact contact) {

        List<EmailDTO> emailDTOs = contactDTO.getEmails();
        emailDTOs = ListUtils.emptyIfNull(emailDTOs);

        try {
            // delete old emails linked to contact
            emailRepository.deleteByContact_ContactId(contact.getContactId());
        } catch (Exception e) {
            log.error(STR."error while deleting emails = \{e.getMessage()}, \{e}");
            throw new EmailNotDeletedException();
        }

        // save and return new emails
        return save(emailDTOs, contact);
    }

    public void handleEmailForImportFile(ContactDTO contactDTO, String currentCellValue, String separationBarRegex, String separationColonRegex) {

        List<EmailDTO> emailDTOs = new ArrayList<>();

        if (!StringUtils.isEmpty(currentCellValue)) {

            // example format emails = "jojo@gmail.com : PERSONAL | fefe@gmail.com : PERSONAL"
            String[] emailList = currentCellValue.split(separationBarRegex);

            for (String email : emailList) {

                EmailDTO emailDTO = new EmailDTO();

                // if data is complete, with 'email address' and 'type'
                if (email.contains(separationColonRegex)) {

                    // example format each email = "jojo@gmail.com : PERSONAL"
                    String[] splitEmail = email.split(separationColonRegex);

                    // handle values libelle and type
                    String libelle = splitEmail[0].trim();
                    String type = splitEmail[1].trim();

                    // get enum value with type
                    EmailTypeEnum emailTypeEnum = EmailTypeEnum.getValue(type);
                    String emailType = String.valueOf(emailTypeEnum);

                    emailDTO.setLibelle(libelle);
                    emailDTO.setType(emailType);
                } else {

                    String valueEmail = email.trim();
                    emailDTO.setLibelle(valueEmail);
                }
                emailDTOs.add(emailDTO);
            }
            contactDTO.setEmails(emailDTOs);
        }
    }
}

