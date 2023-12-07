package com.project.service.implementation;

import com.project.dto.ContactDTO;
import com.project.dto.EmailDTO;
import com.project.entities.Contact;
import com.project.entities.Email;
import com.project.repository.EmailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailServiceImpl {

    private final EmailRepository emailRepository;

    public EmailServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public List<Email> saveEmails(ContactDTO contactDTO, Contact contact) {
        List<Email> emails = new ArrayList<>();
        contactDTO.getEmails().forEach(emailDTO -> {

            Email email = new Email();

            // save each email
            email.setContact(contact);
            email.setLibelle(emailDTO.getLibelle());
            email.setType(emailDTO.getType());

            // save all emails
            emails.add(email);
        });

        emailRepository.saveAll(emails);

        return emails;
    }

    public List<EmailDTO> emailUpdateDTO(List<Email> emailsGetRepo) {
        List<EmailDTO> emailsDTO = new ArrayList<>();
        emailsGetRepo.forEach(email -> {

            EmailDTO emailDTO = new EmailDTO();

            // save each emailDTO
            emailDTO.setEmailId(email.getEmailId());
            emailDTO.setLibelle(email.getLibelle());
            emailDTO.setType(email.getType());

            // save all emailDTOs
            emailsDTO.add(emailDTO);
        });

        return emailsDTO;
    }


}
