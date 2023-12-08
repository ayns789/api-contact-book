package com.project.service;


import com.project.domain.dto.*;
import com.project.domain.entities.Civility;
import com.project.domain.entities.Contact;

import java.util.List;

public interface ContactService {

    ContactDTO create(ContactDTO contactDTO);

    Contact save(ContactDTO contactDTO, Civility civility);

    ContactDTO toDto(Contact contact, CivilityDTO civilityDTO, List<EmailDTO> emailDTOS, List<PhoneDTO> phoneDTOS, List<AddressDTO> addressDTOS);
}
