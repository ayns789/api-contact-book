package com.project.service.implementation;

import com.project.domain.dto.ContactDTO;
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

import java.io.FileNotFoundException;
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
    private Workbook generateExcel() throws FileNotFoundException {

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

    /**
     * Import an Excel file and save contacts in database.
     *
     * @param file The file data of the contact to save.
     */
    public void importFile(MultipartFile file) {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"xlsx".equals(extension)) {
            throw new FileErrorExtensionException();
        }

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
