package com.project.service;


import com.project.dto.*;
import com.project.entities.Civility;
import com.project.entities.Contact;

import java.util.List;

public interface ContactService {

    ContactDTO create(ContactDTO contactDTO);

    Contact save(ContactDTO contactDTO, Civility civility);

    ContactDTO toDto(Contact contact, CivilityDTO civilityDTO, List<EmailDTO> emailDTOS, List<PhoneDTO> phoneDTOS, List<AddressDTO> addressDTOS);
}
