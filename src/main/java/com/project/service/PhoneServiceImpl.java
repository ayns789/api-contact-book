package com.project.service;

import com.project.dto.ContactDTO;
import com.project.dto.PhoneDTO;
import com.project.entities.Contact;
import com.project.entities.Phone;
import com.project.repository.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneServiceImpl {

    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public List<Phone> savePhones(ContactDTO contactDTO, Contact contact) {
        List<Phone> phones = new ArrayList<>();
        contactDTO.getPhones().forEach(phoneDTO -> {

            Phone phone = new Phone();

            // save each phone
            phone.setContact(contact);
            phone.setLibelle(phoneDTO.getLibelle());
            phone.setType(phoneDTO.getType());

            // save all phones
            phones.add(phone);
        });

        phoneRepository.saveAll(phones);

        return phones;
    }


    public List<PhoneDTO> phonesUpdateDTO(List<Phone> phonesGetRepo) {
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phonesGetRepo.forEach(phone -> {

            PhoneDTO phoneDTO = new PhoneDTO();

            // save each phone
            phoneDTO.setPhoneId(phone.getPhoneId());
            phoneDTO.setLibelle(phone.getLibelle());
            phoneDTO.setType(phone.getType());

            // save all phoneDTOs
            phonesDTO.add(phoneDTO);
        });
        return phonesDTO;
    }

}
