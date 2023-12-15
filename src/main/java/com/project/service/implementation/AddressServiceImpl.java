package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CountryDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Country;
import com.project.domain.enums.StreetTypeEnum;
import com.project.exceptions.AddressNotSavedException;
import com.project.repository.AddressRepository;
import com.project.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryServiceImpl countryService;

    @Override
    public List<Address> save(List<AddressDTO> addressDTOS, Contact contact) {

        List<Address> addresses = new ArrayList<>();

        addressDTOS.forEach(addressDTO -> {

            StreetTypeEnum streetType = StreetTypeEnum.getValue(addressDTO.getStreetType());

            Long countryId = addressDTO.getCountry().getCountryId();
            Country country = countryService.getCountryById(countryId);

            Address address = Address.builder()
                    .streetNumber(addressDTO.getStreetNumber())
                    .streetType(streetType)
                    .streetName(addressDTO.getStreetName())
                    .cityName(addressDTO.getCityName())
                    .postalCode(addressDTO.getPostalCode())
                    .contact(contact)
                    .country(country)
                    .build();

            addresses.add(address);
        });

        // save all addresses
        try {
            return addressRepository.saveAll(addresses);
        } catch (Exception e) {
            
            log.error(STR."Error while saving addresses: \{e.getMessage()}", e);
            throw new AddressNotSavedException();
        }
    }

    @Override
    public List<AddressDTO> toDto(List<Address> addresses) {
        return addresses.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public AddressDTO toDto(Address address) {

        CountryDTO countryDTO = countryService.toDto(address.getCountry());

        return AddressDTO.builder()
                .addressId(address.getAddressId())
                .streetNumber(address.getStreetNumber())
                .streetType(address.getStreetType().name())
                .streetName(address.getStreetName())
                .cityName(address.getCityName())
                .postalCode(address.getPostalCode())
                .country(countryDTO)
                .build();
    }
}
