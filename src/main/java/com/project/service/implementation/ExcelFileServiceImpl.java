package com.project.service.implementation;

import com.project.domain.dto.*;
import com.project.domain.entities.Contact;
import com.project.exceptions.FileErrorExtensionException;
import com.project.exceptions.FileExcelNotGeneratedException;
import com.project.service.ExcelFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExcelFileServiceImpl implements ExcelFileService {

    private final ContactServiceImpl contactService;

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
                        .map(address -> STR."\{address.getStreetNumber()} \{address.getStreetType()} \{address.getStreetName()} \{address.getPostalCode()} \{address.getCityName()} \{address.getCountry().getLibelle()}")
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

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"xlsx".equals(extension)) {
            throw new FileErrorExtensionException();
        }

        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> sheetRows = sheet.iterator();

        // get the first row (headerRow)
        Row headerRow = sheetRows.next();
        List<String> headers = new ArrayList<>();
        headerRow.forEach(cell -> headers.add(cell.toString()));

        List<ContactDTO> contactDTOS = fileToDTOs(sheetRows, headers);

//        contactDTOS.forEach(contactDTO -> System.out.println(STR."contactDTO : \{contactDTO}"));
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
    public List<ContactDTO> fileToDTOs(Iterator<Row> sheetRows, List<String> headers) {

        List<ContactDTO> contactDTOs = new ArrayList<>();
        ContactDTO contactDTO = new ContactDTO();

        while (sheetRows.hasNext()) {
            Row currentRow = sheetRows.next();

            // iterate on each cell in row
            for (int i = 0; i < headers.size(); i++) {
                Cell currentCell = currentRow.getCell(i);

                // set contact
                if ("FirstName".equals(headers.get(i))) contactDTO.setFirstName(currentCell.toString());
                if ("LastName".equals(headers.get(i))) contactDTO.setLastName(currentCell.toString());
                if ("Civility".equals(headers.get(i))) {
                    CivilityDTO civilityDTO = new CivilityDTO();
                    if ("MONSIEUR".equals(currentCell.toString())) {
                        civilityDTO.setCivilityId(1L);
                    } else if ("MADAME".equals(currentCell.toString())) {
                        civilityDTO.setCivilityId(2L);
                    } else if ("MADEMOISELLE".equals(currentCell.toString())) {
                        civilityDTO.setCivilityId(3L);
                    } else if ("NON_BINAIRE".equals(currentCell.toString())) {
                        civilityDTO.setCivilityId(4L);
                    } else if ("AUTRE".equals(currentCell.toString())) {
                        civilityDTO.setCivilityId(5L);
                    }
                    contactDTO.setCivility(civilityDTO);
                }

                // set emails
                if ("Email".equals(headers.get(i))) {
                    String emails = "";
                    List<EmailDTO> emailDTOs = new ArrayList<>();
                    emails = currentCell.toString();
                    if (!emails.isEmpty()) {
                        String[] emailList = emails.split("\\s\\|\\s");
                        for (String email : emailList) {
                            EmailDTO emailDTO = new EmailDTO();
                            String[] splitEmail = email.split(":");
                            if (splitEmail.length > 1) {
                                emailDTO.setLibelle(splitEmail[0].trim());
                                emailDTO.setType(splitEmail[1].trim());
                                emailDTOs.add(emailDTO);
                            }
                        }
                        contactDTO.setEmails(emailDTOs);
                    }
                }

                // set phones
                if ("Phone".equals(headers.get(i))) {
                    String phones = "";
                    List<PhoneDTO> phoneDTOs = new ArrayList<>();
                    phones = currentCell.toString();
                    if (!phones.isEmpty()) {
                        String[] phoneList = phones.split("\\s\\|\\s");
                        for (String phone : phoneList) {
                            PhoneDTO phoneDTO = new PhoneDTO();
                            String[] splitPhone = phone.split(":");
                            if (splitPhone.length > 1) {
                                phoneDTO.setLibelle(splitPhone[0].trim());
                                phoneDTO.setType(splitPhone[1].trim());
                                phoneDTOs.add(phoneDTO);
                            }
                        }
                        contactDTO.setPhones(phoneDTOs);
                    }
                }

                // set addresses
                if ("Address".equals(headers.get(i))) {
                    String addresses = "";
                    List<AddressDTO> addressDTOs = new ArrayList<>();
                    addresses = currentCell.toString();
                    if (!addresses.isEmpty()) {
                        String[] addressList = addresses.split("\\s\\|\\s");
                        for (String address : addressList) {
                            AddressDTO addressDTO = new AddressDTO();
                            CountryDTO countryDTO = new CountryDTO();
                            String[] splitAddress = address.split("\\s+");
                            if (splitAddress.length > 1) {
                                addressDTO.setStreetNumber(Integer.valueOf(splitAddress[0]));
                                addressDTO.setStreetType(splitAddress[1]);
                                addressDTO.setStreetName(splitAddress[2]);
                                addressDTO.setPostalCode(Integer.valueOf(splitAddress[3]));
                                addressDTO.setCityName(splitAddress[4]);

                                String[] getCountry = splitAddress[5].split("\\|");
                                String countryValue = getCountry[0];

                                if ("France".equals(countryValue)) {
                                    countryDTO.setCountryId(1L);
                                } else if ("Angleterre".equals(countryValue)) {
                                    countryDTO.setCountryId(2L);
                                } else if ("Espagne".equals(countryValue)) {
                                    countryDTO.setCountryId(3L);
                                } else if ("Italie".equals(countryValue)) {
                                    countryDTO.setCountryId(4L);
                                } else if ("Belgique".equals(countryValue)) {
                                    countryDTO.setCountryId(5L);
                                } else if ("Argentine".equals(countryValue)) {
                                    countryDTO.setCountryId(6L);
                                } else if ("Australie".equals(countryValue)) {
                                    countryDTO.setCountryId(7L);
                                } else if ("Japon".equals(countryValue)) {
                                    countryDTO.setCountryId(8L);
                                } else if ("Russie".equals(countryValue)) {
                                    countryDTO.setCountryId(9L);
                                } else if ("Inde".equals(countryValue)) {
                                    countryDTO.setCountryId(10L);
                                } else if ("Chine".equals(countryValue)) {
                                    countryDTO.setCountryId(11L);
                                }

                                countryDTO.setLibelle(countryValue);
                                addressDTO.setCountry(countryDTO);
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


