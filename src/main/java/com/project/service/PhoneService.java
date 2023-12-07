package com.project.service;

import com.project.dto.PhoneDTO;
import com.project.entities.Contact;
import com.project.entities.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> save(List<PhoneDTO> phoneDTOS, Contact contact);

    List<PhoneDTO> toDto(List<Phone> phones);

    PhoneDTO toDto(Phone phone);
}
