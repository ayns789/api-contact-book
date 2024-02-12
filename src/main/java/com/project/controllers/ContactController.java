package com.project.controllers;

import com.project.domain.dto.ContactDTO;
import com.project.domain.error.ApiError;
import com.project.service.ContactService;
import com.project.service.ExcelFileService;
import com.project.utils.CarnetUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@Log4j2
public class ContactController {

    private final ContactService contactService;
    private final ExcelFileService excelFileService;

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ContactDTO create(@Valid @RequestBody ContactDTO contactDTO) {
        
        log.info("Call add contact");
        return contactService.create(contactDTO);
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ContactDTO getContact(@PathVariable("id") Long id) {

        log.info("Call get contact with id : {}", id);
        return contactService.getContact(id);
    }

    @GetMapping(path = "/byLastName", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContactByLastname(@RequestParam(value = "last_name") String lastName) {

        log.info("Call get contact with lastName : {}", lastName);
        return contactService.getContactByLastname(lastName);
    }

    @GetMapping(path = "/byFirstName", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContactByFirstname(@RequestParam(value = "first_name") String firstName) {

        log.info("Call get contact with firstName : {}", firstName);
        return contactService.getContactByFirstname(firstName);
    }

    @GetMapping(path = "/byPhone", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContactByPhone(@RequestParam(value = "phone_number") String phoneNumber) {

        log.info("Call get contact with phoneNumber : {}", phoneNumber);
        return contactService.getContactByPhone(phoneNumber);
    }

    @PutMapping(path = "/update/{contactId}", consumes = "application/json", produces = "application/json")
    public ContactDTO update(@PathVariable Long contactId, @Valid @RequestBody ContactDTO contactDTO) {

        log.info("Call update contact with id : {}", contactId);
        return contactService.update(contactId, contactDTO);
    }

    @DeleteMapping(path = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ContactDTO delete(@PathVariable("id") Long id) {

        log.info("Call delete contact with id : {}", id);
        return contactService.delete(id);
    }

    // save on pc in file path
    @GetMapping("/export")
    public void exportFile() {

        log.info("Call export excel file representing contacts");
        excelFileService.exportFile();
    }


    @PostMapping("/import")
    public void importFile(@RequestParam("file") MultipartFile file) {

        log.info("Call import excel file contacts to save in database");
        excelFileService.importFile(file);
    }

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
