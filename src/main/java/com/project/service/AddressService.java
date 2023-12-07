package com.project.service;

import com.project.dto.AddressDTO;
import com.project.entities.Address;
import com.project.entities.Contact;

import java.util.List;

public interface AddressService {

    List<Address> save(List<AddressDTO> addresses, Contact contact);

    List<AddressDTO> toDto(List<Address> addresses);

    AddressDTO toDto(Address address);
}
