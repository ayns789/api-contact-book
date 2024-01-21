package com.project.service.implementation;

import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Phone;
import com.project.domain.enums.PhoneTypeEnum;
import com.project.exceptions.PhoneNotDeletedException;
import com.project.exceptions.PhoneNotSavedException;
import com.project.repository.PhoneRepository;
import com.project.service.PhoneService;
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
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    @Override
    public List<Phone> save(List<PhoneDTO> phoneDTOS, Contact contact) {

        List<Phone> phones = new ArrayList<>();

        phoneDTOS.forEach(phoneDTO -> {

            PhoneTypeEnum phoneTypeEnum = PhoneTypeEnum.getValue(phoneDTO.getType());

            Phone phone = Phone.builder()
                    .phoneId(phoneDTO.getPhoneId())
                    .libelle(phoneDTO.getLibelle())
                    .type(phoneTypeEnum)
                    .contact(contact)
                    .build();

            phones.add(phone);
        });

        // save all phones
        try {
            return phoneRepository.saveAll(phones);
        } catch (Exception e) {

            log.error("Error while saving phones: {}", e.getMessage(), e);
            throw new PhoneNotSavedException();
        }
    }

    @Override
    public List<PhoneDTO> toDto(List<Phone> phones) {

        return phones.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public PhoneDTO toDto(Phone phone) {

        return PhoneDTO.builder()
                .phoneId(phone.getPhoneId())
                .libelle(phone.getLibelle())
                .type(phone.getType().name())
                .build();
    }

    @Override
    public List<Phone> toEntity(List<PhoneDTO> phoneDTOs) {

        return phoneDTOs.stream()
                .map(phoneDTO -> Phone.builder()
                        .phoneId(phoneDTO.getPhoneId())
                        .libelle(phoneDTO.getLibelle())
                        .type(PhoneTypeEnum.valueOf(phoneDTO.getType()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Phone> updatePhones(ContactDTO contactDTO, Contact contact) {

        // Todo : ajouter un try catch et dans toutes les méthode update dans les autress servivces
        List<PhoneDTO> phoneDTOs = contactDTO.getPhones();
        phoneDTOs = ListUtils.emptyIfNull(phoneDTOs);

        // Delete phones linked to the contact
        try {
            phoneRepository.deleteByContact_ContactId(contact.getContactId());
        } catch (Exception e) {
            log.error("Error while deleting phones: {}", e.getMessage(), e);
            throw new PhoneNotDeletedException();
        }

        return save(phoneDTOs, contact);
    }

    @Override
    public void deleteAll(List<Phone> phones) {

        try {
            phoneRepository.deleteAllInBatch(phones);
        } catch (Exception e) {
            log.error(STR."error message = \{e.getMessage()}, \{e}");
            throw new PhoneNotDeletedException();
        }
    }

    public void handlePhoneForImportFile(ContactDTO contactDTO, String currentCellValue, String separationBarRegex,
                                         String separationColonRegex) {

        List<PhoneDTO> phoneDTOs = new ArrayList<>();

        if (!StringUtils.isEmpty(currentCellValue)) {

            // example format phones = "0115566887 : PERSONAL | 0115566887 : PERSONAL"
            String[] phoneList = currentCellValue.split(separationBarRegex);

            for (String phone : phoneList) {

                PhoneDTO phoneDTO = new PhoneDTO();

                // if data is complete, with 'number phone' and 'type'
                if (phone.contains(separationColonRegex)) {

                    // example format each phone = "0115566887 : PERSONAL"
                    String[] splitPhone = phone.split(separationColonRegex);

                    String libelle = splitPhone[0].trim();
                    String type = splitPhone[1].trim();

                    // get enum value with type
                    PhoneTypeEnum phoneTypeEnum = PhoneTypeEnum.getValue(type);
                    String phoneType = String.valueOf(phoneTypeEnum);

                    phoneDTO.setLibelle(libelle);
                    phoneDTO.setType(phoneType);
                } else {

                    String phoneValue = phone.trim();
                    phoneDTO.setLibelle(phoneValue);
                }
                phoneDTOs.add(phoneDTO);
            }
            contactDTO.setPhones(phoneDTOs);
        }
    }
}

