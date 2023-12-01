package com.project.service;

import com.project.dto.AddressDTO;
import com.project.dto.ContactDTO;
import com.project.entities.Address;
import com.project.entities.Contact;

import java.util.List;

public interface AddressService {

    List<Address> saveAddresses(ContactDTO contactDTO, Contact contact);

    List<AddressDTO> addressesUpdateDTO(List<Address> addresses);
}
