package com.project.service;

import com.project.dto.AddressDTO;
import com.project.dto.ContactDTO;
import com.project.dto.CountryDTO;
import com.project.entities.Address;
import com.project.entities.Contact;
import com.project.entities.Country;
import com.project.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl {

    private final AddressRepository addressRepository;

    private final CountryServiceImpl countryService;

    public AddressServiceImpl(AddressRepository addressRepository, CountryServiceImpl countryService) {
        this.addressRepository = addressRepository;
        this.countryService = countryService;
    }

    public List<Address> saveAddresses(ContactDTO contactDTO, Contact contact) {
        List<Address> addresses = new ArrayList<>();
        contactDTO.getAddresses().forEach(addressDTO -> {

            Long countryId = addressDTO.getCountry().getCountryId();
            Country country = countryService.getCountryById(countryId);

            Address address = new Address();

            address.setCountry(country);
            address.setContact(contact);
            address.setStreetNumber(addressDTO.getStreetNumber());
            address.setStreetType(addressDTO.getStreetType());
            address.setStreetName(addressDTO.getStreetName());
            address.setCityName(addressDTO.getCityName());
            address.setPostalCode(addressDTO.getPostalCode());

            addresses.add(address);
        });

        addressRepository.saveAll(addresses);

        return addresses;
    }


    public List<AddressDTO> addressesUpdateDTO(List<Address> addresses) {
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addresses.forEach(address -> {

            CountryDTO countryDTO = new CountryDTO();
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setAddressId(address.getAddressId());
            addressDTO.setStreetNumber(address.getStreetNumber());
            addressDTO.setStreetType(address.getStreetType());
            addressDTO.setStreetName(address.getStreetName());
            addressDTO.setCityName(address.getCityName());
            addressDTO.setPostalCode(address.getPostalCode());

            countryDTO.setCountryId(address.getCountry().getCountryId());
            countryDTO.setLibelle(address.getCountry().getLibelle());
            addressDTO.setCountry(countryDTO);

            addressesDTO.add(addressDTO);
        });
        return addressesDTO;
    }
}
