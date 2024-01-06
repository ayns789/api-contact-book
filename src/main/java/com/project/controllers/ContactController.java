package com.project.controllers;

import com.project.domain.dto.ContactDTO;
import com.project.domain.error.ApiError;
import com.project.service.ContactService;
import com.project.utils.CarnetUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ContactDTO create(@Valid @RequestBody ContactDTO contactDTO) {
        return contactService.create(contactDTO);
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ContactDTO getContact(@PathVariable("id") Long id) {
        return contactService.getContact(id);
    }

    @GetMapping(path = "/byLastName", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContactByLastname(@RequestParam(value = "last_name") String lastName) {
        return contactService.getContactByLastname(lastName);
    }

    @GetMapping(path = "/byFirstName", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContactByFirstname(@RequestParam(value = "first_name") String firstName) {
        return contactService.getContactByFirstname(firstName);
    }

    @GetMapping(path = "/byPhone", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContactByPhone(@RequestParam(value = "phone_number") String phoneNumber) {
        return contactService.getContactByPhone(phoneNumber);
    }

    // save on pc in file path
    @GetMapping("/download")
    public void downloadFileContact() throws IOException {
        contactService.downloadFile();
    }

    // save export file with box dialog
    @GetMapping("/export")
    public void exportContacts() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        // define file filters
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers Excel", "xlsx");
        fileChooser.addChoosableFileFilter(filter);

        // viewing box dialog
        int result = fileChooser.showSaveDialog(null);

        // if user selected "save"
        if (result == JFileChooser.APPROVE_OPTION) {
            // get path file
            File file = fileChooser.getSelectedFile();

            // export file excel
            ByteArrayResource resource = contactService.exportExcel();
            Files.write(file.toPath(), resource.getByteArray());
        }
    }

//    // V1 fonctionne, renvoie un fichier excel dans postman
//    @GetMapping("/export")
//    public ResponseEntity<ByteArrayResource> exportContacts() throws IOException {
//        ByteArrayResource resource = contactService.exportExcel();
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contacts.xlsx")
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(resource);
//    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        String messageError =
                STR."The payload is not correct. There are missing or incorrect fields: \{ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> String
                                .format("%s : %s", STR."'\{CarnetUtils.getFieldPath(error)}'", error.getDefaultMessage())
                        )
                        .collect(Collectors.joining(", "))}.";

        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
