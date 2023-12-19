package com.project.controllers;

import com.project.domain.dto.ContactDTO;
import com.project.domain.error.ApiError;
import com.project.service.ContactService;
import com.project.utils.CarnetUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "", consumes = "application/json", produces = "application/json")
    public List<ContactDTO> getContact(@RequestParam(value = "last_name", required = false) String lastName) {

        return contactService.getContact(lastName);
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
