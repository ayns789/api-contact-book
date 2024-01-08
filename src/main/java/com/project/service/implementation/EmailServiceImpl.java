package com.project.service.implementation;

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
import org.springframework.stereotype.Service;

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
    public List<Email> updateEmails(Contact contactId, List<Email> emails, List<EmailDTO> emailDTOs) {

        // delete old emails
        emailRepository.deleteAllInBatch(emails);

        // EmailDTO to Email
        List<Email> newEmails = emailDTOs.stream()
                .map(emailDTO -> Email.builder()
                        .type(EmailTypeEnum.valueOf(emailDTO.getType()))
                        .libelle(emailDTO.getLibelle())
                        .contact(contactId)
                        .build())
                .collect(Collectors.toList());

        // save and return new emails
        return emailRepository.saveAll(newEmails);
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

}
