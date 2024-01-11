package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CivilityDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.EmailDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Address;
import com.project.domain.entities.Civility;
import com.project.domain.entities.Contact;
import com.project.domain.entities.Email;
import com.project.domain.entities.Phone;
import com.project.exceptions.ContactNotDeletedException;
import com.project.exceptions.ContactNotFoundException;
import com.project.exceptions.ContactNotSavedException;
import com.project.exceptions.FileExcelNotGeneratedException;
import com.project.exceptions.FirstnameNotFoundException;
import com.project.exceptions.IdNotFoundException;
import com.project.exceptions.LastnameNotFoundException;
import com.project.exceptions.PhoneNotFoundException;
import com.project.repository.ContactRepository;
import com.project.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
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

    /**
     * Retrieves contacts by last name.
     *
     * @param lastName The lastName of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    public List<ContactDTO> getContactByLastname(String lastName) {

        List<Contact> contacts = contactRepository.getContactByLastname(lastName)
                .orElseThrow(LastnameNotFoundException::new);

        return toDto(contacts);
    }

    /**
     * Retrieves contacts by first name.
     *
     * @param firstName The firstName of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    public List<ContactDTO> getContactByFirstname(String firstName) {

        List<Contact> contacts = contactRepository.getContactByFirstname(firstName)
                .orElseThrow(FirstnameNotFoundException::new);

        return toDto(contacts);
    }

    /**
     * Retrieves contacts by phone numbers.
     *
     * @param phoneNumber The phoneNumber of the contacts.
     * @return The {@link List<ContactDTO>} object representing the retrieved contacts.
     */
    public List<ContactDTO> getContactByPhone(String phoneNumber) {

        List<Contact> contacts = contactRepository.getContactByPhone(phoneNumber)
                .orElseThrow(PhoneNotFoundException::new);

        return toDto(contacts);
    }


    @Override
    public Contact save(ContactDTO contactDTO, Civility civility) {

        // edit contact
        Contact contact = buildContact(contactDTO, civility);

        // handle emails
        List<EmailDTO> emailDTOs = contactDTO.getEmails();
        emailDTOs = ListUtils.emptyIfNull(emailDTOs);
        List<Email> emails = emailService.save(emailDTOs, contact);

        // handle phones
        List<PhoneDTO> phoneDTOs = contactDTO.getPhones();
        phoneDTOs = ListUtils.emptyIfNull(phoneDTOs);
        List<Phone> phones = phoneService.save(phoneDTOs, contact);

        // handle addresses
        List<AddressDTO> addressDTOs = contactDTO.getAddresses();
        addressDTOs = ListUtils.emptyIfNull(addressDTOs);
        List<Address> addresses = addressService.save(addressDTOs, contact);

        // set emails, phones and addresses
        buildContact(contact, emails, phones, addresses);

        // Save contact
        try {
            return contactRepository.save(contact);
        } catch (Exception e) {

            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
            throw new ContactNotSavedException();
        }
    }

//    @Override
//    @Transactional
//    public Contact update(ContactDTO contactDTO, Civility civility) {
//
//        // edit contact
//        Contact contact = buildContact(contactDTO, civility);
//
//        // handle emails
//        List<Email> emails = emailService.updateEmails(contactDTO, contact);
//
//        // handle phones
//        List<Phone> phones = phoneService.updatePhones(contactDTO, contact);
//
//        // handle addresses
//        List<Address> addresses = addressService.updateAddresses(contactDTO, contact);
//
//        // set emails, phones and addresses
//        buildContact(contact, emails, phones, addresses);
//
//        // Save contact
//        try {
//            return contactRepository.save(contact);
//        } catch (Exception e) {
//
//            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
//            throw new ContactNotSavedException();
//        }
//    }

    private void buildContact(Contact contact, List<Email> emails, List<Phone> phones, List<Address> addresses) {
        contact.setEmails(emails);
        contact.setPhones(phones);
        contact.setAddresses(addresses);
    }

    private Contact buildContact(ContactDTO contactDTO, Civility civility) {
        return Contact.builder()
                .contactId(contactDTO.getContactId())
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .civility(civility)
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

    /**
     * Edit a contact by their ID.
     *
     * @param contactId  The id of the contact.
     * @param contactDTO The data of the contact to edit.
     * @return The {@link ContactDTO} object representing the contact edited.
     */
    @Override
    @Transactional
    public ContactDTO update(Long contactId, ContactDTO contactDTO) {

        // Check if contact exist in bdd
        boolean existsById = contactRepository.existsById(contactId);

        if (!existsById) {
            log.error("Contact with id {} not found", contactId);
            throw new ContactNotFoundException();
        }

        // Handle civility
        Long civilityId = contactDTO.getCivility().getCivilityId();
        Civility civility = civilityService.getCivilityById(civilityId);

        // Handle contact
        contactDTO.setContactId(contactId);
        Contact contact = buildContact(contactDTO, civility);

        // handle emails
        List<Email> emails = emailService.updateEmails(contactDTO, contact);

        // handle phones
        List<Phone> phones = phoneService.updatePhones(contactDTO, contact);

        // handle addresses
        List<Address> addresses = addressService.updateAddresses(contactDTO, contact);

        // set emails, phones and addresses
        buildContact(contact, emails, phones, addresses);

        // Save contact
        try {
            contact = contactRepository.save(contact);
        } catch (Exception e) {

            log.error(STR."Error during contact creation: \{e.getMessage()}", e);
            throw new ContactNotSavedException();
        }

        // Build contactDTO
        return toDto(contact);
    }


    /**
     * Delete a contact by its ID.
     *
     * @param id The id of the contact.
     * @return The {@link ContactDTO} object representing the contact deleted.
     */
    @Override
    @Transactional
    public ContactDTO delete(Long id) {

        ContactDTO contactDTO = getContact(id);
        Contact contact = toEntity(contactDTO);

        // delete emails
        if (contact.getEmails() != null && !contact.getEmails().isEmpty()) {
            List<Email> emails = contact.getEmails();
            emailService.deleteAll(emails);
        }

        // delete phones
        List<Phone> phones = contact.getPhones();
        phoneService.deleteAll(phones);

        // delete addresses
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

    /**
     * Generate Workbook with all contacts.
     */
    private Workbook generateExcel() throws FileNotFoundException {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDTO> contactDTOs = toDto(contacts);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contacts");

        // create the header row
        Row headerRow = sheet.createRow(0);

        // set the header row style
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        // font
        headerStyle.setFont(headerFont);
        // background
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // ellipsis
        headerStyle.setWrapText(true);
        // lines borders
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // add values to headerRows
        headerRow.createCell(0).setCellValue("FirstName");
        headerRow.createCell(1).setCellValue("LastName");
        headerRow.createCell(2).setCellValue("Civility");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Address");

        // set columns width to 24 centimeters
        int widthInUnits = (int) (24 * 256); // 1 cm = 256 units in Excel
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.setColumnWidth(i, widthInUnits);
        }

        // add style to headerRows
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
            headerRow.setHeightInPoints((short) 24);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        // add values to rows
        int rowNum = 1;
        for (ContactDTO contact : contactDTOs) {

            //create style rows
            CellStyle rowStyle = workbook.createCellStyle();
            rowStyle.setBorderTop(BorderStyle.THIN);
            rowStyle.setBorderLeft(BorderStyle.THIN);
            rowStyle.setBorderBottom(BorderStyle.THIN);
            rowStyle.setBorderRight(BorderStyle.THIN);

            // create and set values and styles in rows
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contact.getFirstName());
            row.getCell(0).setCellStyle(rowStyle);
            row.createCell(1).setCellValue(contact.getLastName());
            row.getCell(1).setCellStyle(rowStyle);
            row.createCell(2).setCellValue(String.valueOf(contact.getCivility().getLibelle()));
            row.getCell(2).setCellStyle(rowStyle);

            String emailAddress = "";
            if (!contact.getEmails().isEmpty()) {
                emailAddress = contact.getEmails().stream()
                        .map(email -> email.getLibelle() + " : " + email.getType())
                        .collect(Collectors.joining(" | "));
            }
            row.createCell(3).setCellValue(emailAddress);
            row.getCell(3).setCellStyle(rowStyle);

            String phoneNumber = "";
            if (!contact.getPhones().isEmpty()) {
                phoneNumber = contact.getPhones().stream()
                        .map(phone -> phone.getLibelle() + " : " + phone.getType())
                        .collect(Collectors.joining(" | "));
            }
            row.createCell(4).setCellValue(phoneNumber);
            row.getCell(4).setCellStyle(rowStyle);

            String addressList = "";
            if (!contact.getAddresses().isEmpty()) {
                addressList = contact.getAddresses().stream()
                        .map(address -> address.getStreetNumber() + " " + address.getStreetType() + " " + address.getStreetName() + " " +
                                address.getPostalCode() + " " + address.getCityName() + " " + address.getCountry().getLibelle())
                        .collect(Collectors.joining("|"));
            }
            row.createCell(5).setCellValue(addressList);
            row.getCell(5).setCellStyle(rowStyle);

            // add null value after the last column, for last column can use ellipsis
            row.createCell(6).setCellValue("");
        }

        try {
            return workbook;
        } catch (Exception e) {
            log.error(STR."Error during workbook generate operation: \{e.getMessage()}", e);
            throw new FileExcelNotGeneratedException();
        }

    }

    /**
     * Export an Excel file representing the contacts, on a file path.
     */
    public void exportFile() throws IOException {

        // get excel model
        Workbook workbook = generateExcel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
        String filePath = "C:\\Users\\jolya\\bureau\\tempraire\\contacts.xlsx";

        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(resource.getByteArray());
            outputStream.close();
        } catch (Exception e) {
            log.error(STR."Error during FileOutputStream creation operation: \{e.getMessage()}", e);
            throw new FileExcelNotGeneratedException();
        }
    }

    // V1
    public void importFile(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        // get the first row (headerRow)
        Row headerRow = rows.next();
        List<String> headers = new ArrayList<>();
        headerRow.forEach(cell -> headers.add(cell.toString()));

        // initialize list to stock each row
        List<Map<String, String>> data = new ArrayList<>();

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            // initialize map to stock data of each cell with value of each headerRow
            Map<String, String> rowData = new HashMap<>();

            // iterate on each cell in row
            for (int i = 0; i < headers.size(); i++) {
                Cell currentCell = currentRow.getCell(i);
                // convert cell in string and add to map rowData with his headerRow value
                rowData.put(headers.get(i), currentCell.toString());
            }

            // add rowData to data list
            data.add(rowData);
            System.out.println("data : " + data);
        }

        workbook.close();
    }

//    public void importFile(MultipartFile file) throws IOException {
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rows = sheet.iterator();
//        Map<String, Object> contactData = new HashMap<>();
//
//        while (rows.hasNext()) {
//            Row currentRow = rows.next();
//
//            // Get values from each cell and store them in the contactData map
//            contactData.put("firstName", currentRow.getCell(0).getStringCellValue());
//            contactData.put("lastName", currentRow.getCell(1).getStringCellValue());
//            contactData.put("civility", currentRow.getCell(2).getStringCellValue());
//
//            // Handle emails
//            String emails = "";
//            if (!currentRow.getCell(3).getStringCellValue().isEmpty()) {
//                emails = currentRow.getCell(3).getStringCellValue();
//                List<String> emailList = Arrays.asList(emails.split(" | "));
//                for (String email : emailList) {
//                    String[] splitEmail = email.split(":");
//                    String libelle = splitEmail[0];
//                    String type = splitEmail[1];
//                    contactData.put("email" + libelle, type);
//                }
//            }
//
//            // Handle phones
//            String phones = "";
//            if (!currentRow.getCell(4).getStringCellValue().isEmpty()) {
//                phones = currentRow.getCell(4).getStringCellValue();
//                List<String> phoneList = Arrays.asList(phones.split(" | "));
//                for (String phone : phoneList) {
//                    String[] splitPhone = phone.split(":");
//                    String libelle = splitPhone[0];
//                    String type = splitPhone[1];
//                    contactData.put("phone" + libelle, type);
//                }
//            }
//
//            // Handle addresses
//            String address = "";
//            if (!currentRow.getCell(5).getStringCellValue().isEmpty()) {
//                address = currentRow.getCell(5).getStringCellValue();
//                String[] splitAddress = address.split("\\|");
//                List<String> addressList = Arrays.asList(splitAddress);
//                contactData.put("addressLine1", addressList.get(0));
//                contactData.put("addressLine2", addressList.get(1));
//                contactData.put("addressPostalCode", addressList.get(2));
//                contactData.put("addressCity", addressList.get(3));
//                contactData.put("addressCountry", addressList.get(4));
//            }
//        }
//
//        // Process the contactData map and do whatever you need to do with it
//    }
}


