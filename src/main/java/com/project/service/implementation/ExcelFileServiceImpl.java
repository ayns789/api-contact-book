package com.project.service.implementation;

import com.project.domain.dto.AddressDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.entities.Contact;
import com.project.exceptions.FileErrorExtensionException;
import com.project.exceptions.FileExcelNotGeneratedException;
import com.project.service.*;
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
    private final EmailService emailService;
    private final PhoneService phoneService;
    private final AddressService addressService;

    /**
     * Generate Workbook with all contacts.
     */
    private Workbook generateExcel() {

        List<Contact> contacts = contactService.getAllContacts();
        List<ContactDTO> contactDTOs = contactService.toDto(contacts);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contacts");

        // build the header row ( design and values )
        CellStyle headerStyle = designCellStyle(workbook);
        buildHeaderRow(sheet, headerStyle);

        // add values to rows
        int rowNum = 1;

        for (ContactDTO contact : contactDTOs) {

            //create border style rows
            CellStyle rowStyle = createBorderRow(workbook);

            // create and set values and styles in rows
            Row row = sheet.createRow(rowNum++);

            // handle contact
            buildContactRow(contact, rowStyle, row);

            // handle emails
            buildEmailRow(contact, rowStyle, row);

            // handle phones
            buildPhoneRow(contact, rowStyle, row);

            // handle addresses
            buildAddressRow(contact, rowStyle, row);

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

    private CellStyle designCellStyle(Workbook workbook) {

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

        return headerStyle;
    }

    private void buildHeaderRow(Sheet sheet, CellStyle headerStyle) {

        // create the header row
        Row headerRow = sheet.createRow(0);

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
    }

    private CellStyle createBorderRow(Workbook workbook) {

        CellStyle rowStyle = workbook.createCellStyle();
        rowStyle.setBorderTop(BorderStyle.THIN);
        rowStyle.setBorderLeft(BorderStyle.THIN);
        rowStyle.setBorderBottom(BorderStyle.THIN);
        rowStyle.setBorderRight(BorderStyle.THIN);

        return rowStyle;
    }

    private void buildContactRow(ContactDTO contact, CellStyle rowStyle, Row row) {

        row.createCell(0).setCellValue(contact.getFirstName());
        row.getCell(0).setCellStyle(rowStyle);
        row.createCell(1).setCellValue(contact.getLastName());
        row.getCell(1).setCellStyle(rowStyle);
        row.createCell(2).setCellValue(String.valueOf(contact.getCivility().getLibelle()));
        row.getCell(2).setCellStyle(rowStyle);
    }

    private void buildEmailRow(ContactDTO contact, CellStyle rowStyle, Row row) {

        String emailAddress = "";

        if (!contact.getEmails().isEmpty()) {

            emailAddress = contact.getEmails().stream()
                    .map(email -> STR."\{email.getLibelle()} : \{email.getType()}")
                    .collect(Collectors.joining(" | "));
        }

        row.createCell(3).setCellValue(emailAddress);
        row.getCell(3).setCellStyle(rowStyle);
    }

    private void buildPhoneRow(ContactDTO contact, CellStyle rowStyle, Row row) {

        String phoneNumber = "";

        if (!contact.getPhones().isEmpty()) {

            phoneNumber = contact.getPhones().stream()
                    .map(phone -> STR."\{phone.getLibelle()} : \{phone.getType()}")
                    .collect(Collectors.joining(" | "));
        }

        row.createCell(4).setCellValue(phoneNumber);
        row.getCell(4).setCellStyle(rowStyle);
    }

    private void buildAddressRow(ContactDTO contact, CellStyle rowStyle, Row row) {

        String addressList = "";

        if (!contact.getAddresses().isEmpty()) {

            StringBuilder sb = new StringBuilder();

            for (AddressDTO address : contact.getAddresses()) {

                sb.append(STR."\{address.getStreetNumber()} ");
                sb.append(STR."\{address.getStreetType()} ");
                sb.append(STR."\{address.getStreetName()} ");
                sb.append(STR."\{address.getPostalCode()} ");
                sb.append(STR."\{address.getCityName()} ");
                sb.append(address.getCountry().getLibelle());

                if (contact.getAddresses().indexOf(address) < contact.getAddresses().size() - 1) {

                    sb.append(" | ");
                }
            }

            addressList = sb.toString();
        }

        row.createCell(5).setCellValue(addressList);
        row.getCell(5).setCellStyle(rowStyle);
    }

    /**
     * Export an Excel file representing the contacts, on a file path.
     */
    public void exportFile() {

        // get excel model
        Workbook workbook = generateExcel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            workbook.write(baos);
            workbook.close();
        } catch (Exception e) {
            log.error(STR."Error during workbook generate in exportFile() method: \{e.getMessage()}", e);
            throw new FileExcelNotGeneratedException();
        }

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
        List<ContactDTO> contactDTOS = getContactData(sheetRows, headers);

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
    public List<ContactDTO> getContactData(Iterator<Row> sheetRows, List<String> headers) {

        List<ContactDTO> contactDTOs = new ArrayList<>();

        // initialize constants to compare headers values
        final String FIRSTNAME = "FirstName";
        final String LASTNAME = "LastName";
        final String CIVILITY = "Civility";
        final String EMAIL = "Email";
        final String PHONE = "Phone";
        final String ADDRESS = "Address";

        while (sheetRows.hasNext()) {

            ContactDTO contactDTO = new ContactDTO();

            Row currentRow = sheetRows.next();

            // iterate on each cell in row
            for (int i = 0; i < headers.size(); i++) {

                // get value of current cell
                Cell currentCell = currentRow.getCell(i);
                String currentCellValue = currentCell.getStringCellValue().trim();

                // build contact
                if (headers.get(i).equalsIgnoreCase(FIRSTNAME)) {

                    contactDTO.setFirstName(currentCellValue);
                }

                if (headers.get(i).equalsIgnoreCase(LASTNAME)) {

                    contactDTO.setLastName(currentCellValue);
                }

                // Handle Civility
                if (headers.get(i).equalsIgnoreCase(CIVILITY)) {

                    civilityService.handleCivilityForImportFile(contactDTO, currentCellValue);
                }

                final String SEPARATION_BAR_REGEX = "\\| ";
                final String SEPARATION_COLON_REGEX = ":";

                // build emails
                if (headers.get(i).equalsIgnoreCase(EMAIL)) {

                    emailService.handleEmailForImportFile(contactDTO, currentCellValue, SEPARATION_BAR_REGEX, SEPARATION_COLON_REGEX);
                }

                // build phones
                if (headers.get(i).equalsIgnoreCase(PHONE)) {

                    phoneService.handlePhoneForImportFile(contactDTO, currentCellValue, SEPARATION_BAR_REGEX, SEPARATION_COLON_REGEX);
                }

                // build addresses
                if (headers.get(i).equalsIgnoreCase(ADDRESS)) {

                    addressService.handleAddressForImportFile(contactDTO, currentCellValue, SEPARATION_BAR_REGEX);
                }
            }
            contactDTOs.add(contactDTO);
        }
        return contactDTOs;
    }
}


