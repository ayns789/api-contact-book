package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.CivilityDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.dto.CountryDTO;
import com.project.domain.dto.EmailDTO;
import com.project.domain.dto.PhoneDTO;
import com.project.domain.entities.Contact;
import com.project.domain.enums.CivilityEnumType;
import com.project.exceptions.FileErrorExtensionException;
import com.project.exceptions.FileExcelNotGeneratedException;
import com.project.service.CivilityService;
import com.project.service.ContactService;
import com.project.service.CountryService;
import com.project.service.ExcelFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExcelFileServiceImpl implements ExcelFileService {

    private final ContactService contactService;
    private final CivilityService civilityService;
    private final CountryService countryService;

    /**
     * Generate Workbook with all contacts.
     */
    private Workbook generateExcel() {

        List<Contact> contacts = contactService.getAllContacts();
        List<ContactDTO> contactDTOs = contactService.toDto(contacts);

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
                    .map(email -> STR."\{email.getLibelle()} : \{email.getType()}")
                    .collect(Collectors.joining(" | "));
            }
            row.createCell(3).setCellValue(emailAddress);
            row.getCell(3).setCellStyle(rowStyle);

            String phoneNumber = "";
            if (!contact.getPhones().isEmpty()) {
                phoneNumber = contact.getPhones().stream()
                    .map(phone -> STR."\{phone.getLibelle()} : \{phone.getType()}")
                    .collect(Collectors.joining(" | "));
            }
            row.createCell(4).setCellValue(phoneNumber);
            row.getCell(4).setCellStyle(rowStyle);

            String addressList = "";
            if (!contact.getAddresses().isEmpty()) {
                addressList = contact.getAddresses().stream()
                    .map(
                        address -> STR."\{address.getStreetNumber()} \{address.getStreetType()} \{address.getStreetName()} \{address.getPostalCode()} \{address.getCityName()} \{address.getCountry()
                            .getLibelle()}")
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

    /**
     * Import an Excel file and extract its data to save in database.
     *
     * @param file The file contact to save.
     */
    public void importFile(MultipartFile file) {

        // check extension file for "xlsx"
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String EXCEL_EXTENSION = "xlsx";
        if (!EXCEL_EXTENSION.equals(extension)) {
            throw new FileErrorExtensionException();
        }

        // initialize workbook
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // get values of rows
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> sheetRows = sheet.iterator();

        // get values of headers
        Row headerRow = sheetRows.next();
        List<String> headers = new ArrayList<>();
        headerRow.forEach(cell -> headers.add(cell.toString()));

        // get contacts generated by data file
        List<ContactDTO> contactDTOS = getContactsData(sheetRows, headers);

        // save in database List<ContactDTO> extracted from file
        contactDTOS.forEach(contactService::create);

        try {
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve contacts from the file to turn them into a contactDTO list.
     *
     * @param sheetRows The values of rows data some file.
     * @param headers   The values of header data some file.
     * @return The {@link List<ContactDTO>} object representing the contacts on the file.
     */
    public List<ContactDTO> getContactsData(Iterator<Row> sheetRows, List<String> headers) {

        List<ContactDTO> contactDTOs = new ArrayList<>();

        while (sheetRows.hasNext()) {

            ContactDTO contactDTO = new ContactDTO();

            Row currentRow = sheetRows.next();

            // iterate on each cell in row
            for (int i = 0; i < headers.size(); i++) {

                // get value of current cell
                Cell currentCell = currentRow.getCell(i);
                String currentCellValue = currentCell.getStringCellValue().trim();

                // build contact
                if (headers.get(0).equalsIgnoreCase(headers.get(i))) {
                    contactDTO.setFirstName(currentCellValue);
                }

                if (headers.get(1).equalsIgnoreCase(headers.get(i))) {
                    contactDTO.setLastName(currentCellValue);
                }

                // Handle Civility
                if (headers.get(2).equalsIgnoreCase(headers.get(i))) {

                    CivilityEnumType civilityEnumType = CivilityEnumType.getValue(currentCellValue);
                    CivilityDTO civilityDTO = civilityService.findByLibelle(civilityEnumType);
                    contactDTO.setCivility(civilityDTO);
                }

                final String REG_OTHER_DATA = "\\|";
                final String REG_OTHER_DATA_TYPE = ":";

                // build emails
                if (headers.get(3).equalsIgnoreCase(headers.get(i))) {

                    List<EmailDTO> emailDTOs = new ArrayList<>();

                    if (!StringUtils.isEmpty(currentCellValue)) {
                        // example format emails = "email | email | email"
                        String[] emailList = currentCellValue.split(REG_OTHER_DATA);

                        for (String email : emailList) {
                            EmailDTO emailDTO = new EmailDTO();

                            // if data is complete, with 'email address' and 'type'
                            if (email.contains(REG_OTHER_DATA_TYPE)) {

                                // example format each email = "jojo@gmail.com : PERSONAL"
                                String[] splitEmail = email.split(REG_OTHER_DATA_TYPE);

                                if (splitEmail.length > 1) {
                                    emailDTO.setLibelle(splitEmail[0].trim());
                                    emailDTO.setType(splitEmail[1].trim());
                                    emailDTOs.add(emailDTO);
                                }
                            } else {
                                String valueEmail = email.trim();
                                emailDTO.setLibelle(valueEmail);
                                emailDTOs.add(emailDTO);
                            }
                        }
                        contactDTO.setEmails(emailDTOs);
                    }
                }

                // build phones
                if (headers.get(4).equalsIgnoreCase(headers.get(i))) {
                    List<PhoneDTO> phoneDTOs = new ArrayList<>();

                    if (!StringUtils.isEmpty(currentCellValue)) {
                        // example format phones = "0115566887 : PERSONAL | 0115566887 : PERSONAL | 0115566887 : PERSONAL"
                        String[] phoneList = currentCellValue.split(REG_OTHER_DATA);

                        for (String phone : phoneList) {
                            PhoneDTO phoneDTO = new PhoneDTO();

                            // if data is complete, with 'number phone' and 'type'
                            if (phone.contains(REG_OTHER_DATA_TYPE)) {

                                // example format each phone = "0115566887 : PERSONAL"
                                String[] splitPhone = phone.split(REG_OTHER_DATA_TYPE);

                                if (splitPhone.length > 1) {
                                    phoneDTO.setLibelle(splitPhone[0].trim());
                                    phoneDTO.setType(splitPhone[1].trim());
                                    phoneDTOs.add(phoneDTO);
                                }
                            } else {
                                String phoneValue = phone.trim();
                                phoneDTO.setLibelle(phoneValue);
                                phoneDTOs.add(phoneDTO);
                            }

                        }
                        contactDTO.setPhones(phoneDTOs);
                    }
                }

                // build addresses
                if (headers.get(5).equalsIgnoreCase(headers.get(i))) {

                    List<AddressDTO> addressDTOs = new ArrayList<>();

                    if (!StringUtils.isEmpty(currentCellValue)) {
                        // example format addresses = "address | address | address"
                        String[] addressList = currentCellValue.split(REG_OTHER_DATA);

                        for (String address : addressList) {
                            AddressDTO addressDTO = new AddressDTO();
                            // example format each address = "55 STREET Dobenton 75014 Paris France"
                            String REG_SPACE = "\\s+";
                            String[] splitAddress = address.split(REG_SPACE);

                            if (splitAddress.length > 1) {
                                addressDTO.setStreetNumber(Integer.valueOf(splitAddress[0]));
                                addressDTO.setStreetType(splitAddress[1]);
                                addressDTO.setStreetName(splitAddress[2]);
                                addressDTO.setPostalCode(Integer.valueOf(splitAddress[3]));
                                addressDTO.setCityName(splitAddress[4]);

                                // get last data in splitAddress = get country
                                String[] getCountry = splitAddress[5].split(REG_OTHER_DATA);
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
            contactDTOs.add(contactDTO);
        }
        return contactDTOs;
    }
}


