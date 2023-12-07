package com.project.service;

import com.project.dto.EmailDTO;
import com.project.entities.Contact;
import com.project.entities.Email;

import java.util.List;

public interface EmailService {

    List<Email> save(List<EmailDTO> emailDTOS, Contact contact);

    List<EmailDTO> toDto(List<Email> emails);

    EmailDTO toDto(Email email);
}
