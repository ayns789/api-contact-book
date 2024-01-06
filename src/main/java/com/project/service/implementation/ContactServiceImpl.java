package com.project.service.implementation;

import com.project.domain.dto.*;
import com.project.domain.entities.*;
import com.project.exceptions.*;
import com.project.repository.ContactRepository;
import com.project.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.output.ByteArrayOutputStream;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final EmailServiceImpl emailService;
    private final PhoneServiceImpl phoneService;
    private final CivilityServiceImpl civilityService;
    private final AddressServiceImpl addressService;
    private final Map<String, Integer> headerMap = new HashMap<>();


    @Override
    @Transactional
    public ContactDTO create(ContactDTO contactDTO) {

        // Handle civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // Handle contact
        Contact contact = save(contactDTO, civility);

        // Build contactDTO
        return toDto(contact);
    }

    /**
     * Retrieves a contact by its ID.
     *
     * @param id The ID of the contact.
     * @return The {@link ContactDTO} object representing the retrieved contact.
     */
    public ContactDTO getContact(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);

        // Save contactDTO
        return toDto(contact);
    }

    public List<ContactDTO> getContactByLastname(String lastName) {

        List<Contact> contacts = contactRepository.getContactByLastname(lastName)
                .orElseThrow(LastnameNotFoundException::new);

        return toDto(contacts);
    }

    public List<ContactDTO> getContactByFirstname(String firstName) {

        List<Contact> contacts = contactRepository.getContactByFirstname(firstName)
                .orElseThrow(FirstnameNotFoundException::new);

        return toDto(contacts);
    }

    public List<ContactDTO> getContactByPhone(String phoneNumber) {

        List<Contact> contacts = contactRepository.getContactByPhone(phoneNumber)
                .orElseThrow(PhoneNotFoundException::new);

        return toDto(contacts);
    }


    @Override
    public Contact save(ContactDTO contactDTO, Civility civility) {

        Contact contact = Contact.builder()
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .civility(civility)
                .build();

        // Save contact
        try {
            return contactRepository.save(contact);
        } catch (Exception e) {

            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
            throw new ContactNotSavedException();
        }
    }

    @Override
    @Transactional
    public ContactDTO update(Long contactId, ContactDTO contactDTO) {

        // check and get contact, if contact exist
        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(ContactNotFoundException::new);

        // get civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // update contact
        if (civility != null) {
            existingContact.setCivility(civility);
        }

        if (contactDTO.getFirstName() != null && !contactDTO.getFirstName().isEmpty()) {
            if (!existingContact.getFirstName().equals(contactDTO.getFirstName())) {
                existingContact.setFirstName(contactDTO.getFirstName());
            }
        }

        if (contactDTO.getLastName() != null && !contactDTO.getLastName().isEmpty()) {
            if (!existingContact.getLastName().equals(contactDTO.getLastName())) {
                existingContact.setLastName(contactDTO.getLastName());
            }
        }

        // update emails
        if (contactDTO.getEmails() != null && !contactDTO.getEmails().isEmpty()) {

            List<Email> oldEmails = existingContact.getEmails();
            List<EmailDTO> newEmailDTOs = contactDTO.getEmails();
            List<Email> emailsUpdated = emailService.updateEmails(existingContact, oldEmails, newEmailDTOs);

            try {
                existingContact.setEmails(emailsUpdated);
            } catch (Exception e) {
                throw new EmailNotSavedException();
            }
        }

        // update phones
        if (contactDTO.getPhones() != null && !contactDTO.getPhones().isEmpty()) {

            List<Phone> oldPhones = existingContact.getPhones();
            List<PhoneDTO> newPhoneDTOs = contactDTO.getPhones();
            List<Phone> phonesUpdated = phoneService.updatePhones(existingContact, oldPhones, newPhoneDTOs);

            // save phone list updated
            try {
                existingContact.setPhones(phonesUpdated);
            } catch (Exception e) {
                throw new PhoneNotSavedException();
            }
        }

        if (contactDTO.getAddresses() != null && !contactDTO.getAddresses().isEmpty()) {
            List<Address> oldAddresses = existingContact.getAddresses();
            List<AddressDTO> newAddressDTOs = contactDTO.getAddresses();
            List<Address> addressUpdated = addressService.updateAddresses(existingContact, oldAddresses, newAddressDTOs);

            try {
                existingContact.setAddresses(addressUpdated);
            } catch (Exception e) {
                throw new AddressNotSavedException();
            }
        }

        // save contact with new data
        try {
            Contact updatedContact = contactRepository.save(existingContact);
            return toDto(updatedContact);
        } catch (Exception e) {
            log.error("Error during contact update: {}", e.getMessage(), e);
            throw new ContactNotUpdatedException();
        }
    }

    @Override
    public ContactDTO delete(Long id) {

        ContactDTO contactDTO = getContact(id);
        Contact contact = toEntity(contactDTO);

        // delete data linked to contact
        if (contact.getEmails() != null && !contact.getEmails().isEmpty()) {
            List<Email> emails = contact.getEmails();
            emailService.deleteAll(emails);
        }

        List<Phone> phones = contact.getPhones();
        phoneService.deleteAll(phones);

        if (contact.getAddresses() != null && !contact.getAddresses().isEmpty()) {
            List<Address> addresses = contact.getAddresses();
            addressService.deleteAll(addresses);
        }

        // delete contact
        contactRepository.deleteById(contact.getContactId());

        try {
            return toDto(contact);
        } catch (Exception e) {
            log.error(STR."Error during contact deleted operation: \{e.getMessage()}", e);
            throw new ContactNotDeletedException();
        }
    }

    public Contact toEntity(ContactDTO contactDTO) {
        Civility civility = civilityService.toEntity(contactDTO.getCivility());

        List<Email> emails = emailService.toEntity(contactDTO.getEmails());
        emails = ListUtils.emptyIfNull(emails);

        List<Phone> phones = phoneService.toEntity(contactDTO.getPhones());
        phones = ListUtils.emptyIfNull(phones);

        List<Address> addresses = addressService.toEntity(contactDTO.getAddresses());
        addresses = ListUtils.emptyIfNull(addresses);

        return Contact.builder()
                .contactId(contactDTO.getContactId())
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .civility(civility)
                .emails(emails)
                .phones(phones)
                .addresses(addresses)
                .build();
    }

    @Override
    public ContactDTO toDto(Contact contact) {

        CivilityDTO civilityDTO = civilityService.toDto(contact.getCivility());

        List<EmailDTO> emailDTOs = emailService.toDto(contact.getEmails());
        emailDTOs = ListUtils.emptyIfNull(emailDTOs);

        List<PhoneDTO> phoneDTOs = phoneService.toDto(contact.getPhones());
        phoneDTOs = ListUtils.emptyIfNull(phoneDTOs);

        List<AddressDTO> addressDTOs = addressService.toDto(contact.getAddresses());
        addressDTOs = ListUtils.emptyIfNull(addressDTOs);

        return ContactDTO.builder()
                .contactId(contact.getContactId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .civility(civilityDTO)
                .emails(emailDTOs)
                .phones(phoneDTOs)
                .addresses(addressDTOs)
                .build();
    }

    @Override
    public List<ContactDTO> toDto(List<Contact> contacts) {
        return contacts.stream()
                .map(this::toDto)
                .toList();
    }

    public Workbook generateExcel() throws FileNotFoundException {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDTO> contactDTOs = toDto(contacts);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contacts");

        // Create the header row
        Row headerRow = sheet.createRow(0);

        // Set the header row style
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // add values to headerRow
        headerRow.createCell(0).setCellValue("FirstName");
        headerRow.createCell(1).setCellValue("LastName");
        headerRow.createCell(2).setCellValue("Civility");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Address");

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (ContactDTO contact : contactDTOs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contact.getFirstName());
            row.createCell(1).setCellValue(contact.getLastName());
            row.createCell(2).setCellValue(String.valueOf(contact.getCivility().getLibelle()));

            String emailAddress = "";
            if (!contact.getEmails().isEmpty()) {
                emailAddress = contact.getEmails().stream()
                        .map(email -> email.getLibelle() + " : " + email.getType())
                        .collect(Collectors.joining(" | "));
            }
            row.createCell(3).setCellValue(emailAddress);

            String phoneNumber = "";
            if (!contact.getPhones().isEmpty()) {
                phoneNumber = contact.getPhones().stream()
                        .map(phone -> phone.getLibelle() + " : " + phone.getType())
                        .collect(Collectors.joining(" | "));
            }
            row.createCell(5).setCellValue(phoneNumber);

            String addressList = "";
            if (!contact.getAddresses().isEmpty()) {
                addressList = contact.getAddresses().stream()
                        .map(address -> address.getStreetNumber() + " " + address.getStreetType() + " " + address.getStreetName() + " " + address.getPostalCode() + " " + address.getCityName() + " " + address.getCountry().getLibelle())
                        .collect(Collectors.joining("|"));
            }
            row.createCell(7).setCellValue(addressList);

        }

        // the columns take size of values
        for (int i = 0; i <= 8; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }


    public void downloadFile() throws IOException {

        ByteArrayResource resource = exportExcel();
        String filePath = "C:\\Users\\jolya\\bureau\\tempraire\\contacts.xlsx";

        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(resource.getByteArray());
        outputStream.close();
    }

//    // to export file with box dialog
//    @Override
//    public FileOutputStream exportExcel() throws IOException {
//        Workbook workbook = generateExcel();
//        ByteArrayResource resource = streamExcel(workbook);
//
//        ByteArrayOutputStream baos = resource.getByteArrayOutputStream();
//
//        File file = new File("myfile.xlsx");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//        FileOutputStream outputStream = new FileOutputStream(file);
//        baos.writeTo(outputStream);
//
//        outputStream.close();
//        baos.close();
//
//        return outputStream;
//    }

    @Override
    public ByteArrayResource exportExcel() throws IOException {
        Workbook workbook = generateExcel();
        ByteArrayResource resource = streamExcel(workbook);

        return resource;
    }

    private ByteArrayResource streamExcel(Workbook workbook) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
        return resource;
    }
}


