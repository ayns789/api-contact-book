package com.project.service;

import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> save(List<PhoneDTO> phoneDTOS, Contact contact);

    List<PhoneDTO> toDto(List<Phone> phones);

    PhoneDTO toDto(Phone phone);
}
