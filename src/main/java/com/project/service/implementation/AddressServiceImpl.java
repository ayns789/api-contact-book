package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CountryDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Country;
import com.project.domain.enums.PhoneTypeEnum;
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

    @Override
    public List<Address> updateAddress(List<Address> oldAddresses, List<AddressDTO> newAddressDTOs) {

        List<Address> updatedAddresses = new ArrayList<>();
        for(int i = 0; i < oldAddresses.size(); i++){
            // get each country of new address
            Long countryId = newAddressDTOs.get(i).getCountry().getCountryId();
            Country country = countryService.getCountryById(countryId);

            // get each old and new address
            Address oldAddress = oldAddresses.get(i);
            AddressDTO newAddressDto = newAddressDTOs.get(i);

            // compare and update data if changes are detected
            if(!oldAddress.getStreetNumber().equals(newAddressDto.getStreetNumber())){
                oldAddress.setStreetNumber(newAddressDto.getStreetNumber());
            }
            if(!oldAddress.getStreetType().equals(StreetTypeEnum.valueOf(newAddressDto.getStreetType()))){
                oldAddress.setStreetType(StreetTypeEnum.valueOf(newAddressDto.getStreetType()));
            }
            if(!oldAddress.getStreetName().equals(newAddressDto.getStreetName())){
                oldAddress.setStreetName(newAddressDto.getStreetName());
            }
            if(!oldAddress.getCityName().equals(newAddressDto.getCityName())){
                oldAddress.setCityName(newAddressDto.getCityName());
            }
            if(!oldAddress.getPostalCode().equals(newAddressDto.getPostalCode())){
                oldAddress.setPostalCode(newAddressDto.getPostalCode());
            }
            if(country != null){
                oldAddress.setCountry(country);
            }
            // save in list
            updatedAddresses.add(oldAddress);
        }

        // return list
        return updatedAddresses;
    }
}
