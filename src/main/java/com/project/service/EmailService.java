package com.project.service;

import com.project.dto.ContactDTO;
import com.project.dto.EmailDTO;
import com.project.entities.Contact;
import com.project.entities.Email;

import java.util.List;

public interface EmailService {

    List<Email> saveEmails(ContactDTO contactDTO, Contact contact);

    List<EmailDTO> emailUpdateDTO(List<Email> emailsGetRepo);
}
