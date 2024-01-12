package com.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelFileService {

    /**
     * Export an Excel file representing the contacts, on a file path.
     */
    void exportFile() throws IOException;

    /**
     * Import an Excel file and save contacts in database.
     *
     * @param file The file data of the contact to save.
     */
    void importFile(MultipartFile file) throws IOException;
}
