package com.project.service;

import com.project.domain.dto.EmailDTO;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Email;

import java.util.List;

public interface EmailService {

    List<Email> save(List<EmailDTO> emailDTOS, Contact contact);

    List<Email> updateEmails(Contact contactId, List<Email> oldEmails, List<EmailDTO> newEmailDTOs);

    List<EmailDTO> toDto(List<Email> emails);

    EmailDTO toDto(Email email);

    void deleteAll(List<Email> emails);

    List<Email> toEntity(List<EmailDTO> emailDTOs);
}
