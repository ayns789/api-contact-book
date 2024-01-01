package com.project.service.implementation;

import com.project.domain.dto.EmailDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Email;
import com.project.domain.entities.Phone;
import com.project.domain.enums.EmailTypeEnum;
import com.project.domain.enums.PhoneTypeEnum;
import com.project.exceptions.PhoneNotSavedException;
import com.project.repository.PhoneRepository;
import com.project.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Phone> updatePhone(List<Phone> oldPhones, List<PhoneDTO> newPhoneDTOs) {
        List<Phone> updatedPhones = new ArrayList<>();
        for (int i = 0; i < oldPhones.size(); i++) {
            // get each old phone and new phone
            Phone oldPhone = oldPhones.get(i);
            PhoneDTO newPhoneDTO = newPhoneDTOs.get(i);

            // compare and update data if changes are detected
            if (!oldPhone.getLibelle().equals(newPhoneDTO.getLibelle())) {
                oldPhone.setLibelle(newPhoneDTO.getLibelle());
            }
            if (!oldPhone.getType().equals(PhoneTypeEnum.valueOf(newPhoneDTO.getType()))) {
                oldPhone.setType(PhoneTypeEnum.valueOf(newPhoneDTO.getType()));
            }
            // save in list
            updatedPhones.add(oldPhone);
        }
        // return list updated
        return updatedPhones;
    }

}
