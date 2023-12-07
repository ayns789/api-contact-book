package com.project.service.implementation;

import com.project.dto.EmailDTO;
import com.project.entities.Contact;
import com.project.entities.Email;
import com.project.enums.EmailTypeEnum;
import com.project.repository.EmailRepository;
import com.project.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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
        return emailRepository.saveAll(emails);
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
}
