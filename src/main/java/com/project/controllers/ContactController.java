package com.project.controllers;

import com.project.domain.dto.ContactDTO;
import com.project.domain.error.ApiError;
import com.project.service.ContactService;
import com.project.utils.CarnetUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

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

    @PutMapping(path = "/update/{contactId}", consumes = "application/json", produces = "application/json")
    public ContactDTO update(@PathVariable Long contactId, @Valid @RequestBody ContactDTO updateContactDTO) {
        return contactService.update(contactId, updateContactDTO);
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

    @DeleteMapping(path = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ContactDTO delete(@PathVariable("id") Long id) {
        return contactService.delete(id);
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
