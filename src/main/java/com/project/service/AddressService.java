package com.project.service;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Contact;

import java.util.List;

public interface AddressService {

    List<Address> save(List<AddressDTO> addresses, Contact contact);

    List<AddressDTO> toDto(List<Address> addresses);

    AddressDTO toDto(Address address);

    List<Address> updateAddresses(Contact contactId, List<Address> oldAddresses, List<AddressDTO> newAddressDTOs);

    void deleteAll(List<Address> addresses);

    List<Address> updateAddresses(ContactDTO contactDTO, Contact contact);
}
