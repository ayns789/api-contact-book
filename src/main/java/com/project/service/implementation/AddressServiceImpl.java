package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.CountryDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Country;
import com.project.domain.enums.StreetTypeEnum;
import com.project.exceptions.AddressNotDeletedException;
import com.project.exceptions.AddressNotSavedException;
import com.project.repository.AddressRepository;
import com.project.service.AddressService;
import com.project.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryService countryService;

    @Override
    public List<Address> save(List<AddressDTO> addressDTOS, Contact contact) {

        List<Address> addresses = new ArrayList<>();

        addressDTOS.forEach(addressDTO -> {

            StreetTypeEnum streetType = StreetTypeEnum.getValue(addressDTO.getStreetType());

            Long countryId = addressDTO.getCountry().getCountryId();
            Country country = countryService.getCountryById(countryId);

            Address address = Address.builder()
                    .addressId(addressDTO.getAddressId())
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

    public List<Address> toEntity(List<AddressDTO> addressDTOs) {

        return addressDTOs.stream()
                .map(addressDTO -> Address.builder()
                        .addressId(addressDTO.getAddressId())
                        .streetNumber(addressDTO.getStreetNumber())
                        .streetType(StreetTypeEnum.valueOf(addressDTO.getStreetType()))
                        .streetName(addressDTO.getStreetName())
                        .cityName(addressDTO.getCityName())
                        .postalCode(addressDTO.getPostalCode())
                        .country(countryService.getCountryById(addressDTO.getCountry().getCountryId()))
                        .build())
                .collect(Collectors.toList());
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
    public void deleteAll(List<Address> addresses) {

        try {
            addressRepository.deleteAllInBatch(addresses);
        } catch (Exception e) {
            log.error(STR."error message = \{e.getMessage()}, \{e}");
            throw new AddressNotDeletedException();
        }
    }

    @Override
    @Transactional
    public List<Address> updateAddresses(ContactDTO contactDTO, Contact contact) {

        List<AddressDTO> addressDTOs = contactDTO.getAddresses();
        addressDTOs = ListUtils.emptyIfNull(addressDTOs);

        try {
            // delete old addresses linked to contact
            addressRepository.deleteByContact_ContactId(contact.getContactId());
        } catch (Exception e) {
            log.error(STR."error while deleting addresses = \{e.getMessage()}, \{e}");
            throw new AddressNotDeletedException();
        }

        return save(addressDTOs, contact);
    }

    public void handleAddressForImportFile(ContactDTO contactDTO, String currentCellValue, String separationBarRegex) {

        List<AddressDTO> addressDTOs = new ArrayList<>();

        if (!StringUtils.isEmpty(currentCellValue)) {

            // example format addresses =
            // "55 STREET Dobenton 75014 Paris France | 28 STREET Charogne 75012 Paris France"
            String[] addressList = currentCellValue.split(separationBarRegex);

            for (String address : addressList) {

                AddressDTO addressDTO = new AddressDTO();

                // example format each address = "55 STREET Dobenton 75014 Paris France"
                final String SPACE_REGEX = "\\s+";
                String[] splitAddress = address.split(SPACE_REGEX);

                if (splitAddress.length > 1) {

                    addressDTO.setStreetNumber(Integer.valueOf(splitAddress[0]));
                    addressDTO.setStreetType(splitAddress[1]);
                    addressDTO.setStreetName(splitAddress[2]);
                    addressDTO.setPostalCode(Integer.valueOf(splitAddress[3]));
                    addressDTO.setCityName(splitAddress[4]);

                    // get last data in splitAddress = get country
                    String[] getCountry = splitAddress[5].split(separationBarRegex);
                    String countryValue = getCountry[0].trim();

                    // get country by libelle value
                    CountryDTO countryDTO = countryService.findByLibelle(countryValue);

                    // set country
                    addressDTO.setCountry(countryDTO);

                    // add address to addresses list
                    addressDTOs.add(addressDTO);
                }
            }
            contactDTO.setAddresses(addressDTOs);
        }
    }

}

