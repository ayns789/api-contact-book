package com.project.service;

import com.project.domain.dto.AddressDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Contact;

import java.util.List;

public interface AddressService {

    List<Address> save(List<AddressDTO> addresses, Contact contact);

    List<AddressDTO> toDto(List<Address> addresses);

    AddressDTO toDto(Address address);

    List<Address> updateAddress(List<Address> oldAddresses, List<AddressDTO> newAddressDTOs);
}
