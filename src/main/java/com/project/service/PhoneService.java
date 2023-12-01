package com.project.service;

import com.project.dto.ContactDTO;
import com.project.dto.PhoneDTO;
import com.project.entities.Contact;
import com.project.entities.Phone;

import java.util.List;

public interface PhoneService {
    List<Phone> savePhones(ContactDTO contactDTO, Contact contact);

    List<PhoneDTO> phonesUpdateDTO(List<Phone> phonesGetRepo);
}
