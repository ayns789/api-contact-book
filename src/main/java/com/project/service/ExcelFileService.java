package com.project.service;

import com.project.domain.dto.ContactDTO;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;

public interface ExcelFileService {

    /**
     * Export an Excel file representing the contacts, on a file path.
     */
    void exportFile();

    /**
     * Import an Excel file and extract its data to save in database.
     *
     * @param file The file contact to save.
     */
    void importFile(MultipartFile file);

    /**
     * Retrieve contacts from the file to turn them into a contactDTO list.
     *
     * @param sheetRows The values of rows data some file.
     * @param headers   The values of header data some file.
     * @return The {@link List<ContactDTO>} object representing the contacts on the file.
     */
    List<ContactDTO> getContactData(Iterator<Row> sheetRows, List<String> headers);
}
