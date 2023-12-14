package com.project.controllers;

import com.project.domain.dto.ContactDTO;
import com.project.domain.error.ApiError;
import com.project.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ContactDTO create(@Valid @RequestBody ContactDTO contactDTO, WebRequest request) {
        return contactService.create(contactDTO);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        String messageError = "The payload is not correct. There are missing or incorrect fields: " +
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> String.format("%s : %s", error.getCode(), error.getDefaultMessage()))
                        .collect(Collectors.joining(" , "));

        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errors.put(error.getField(), error.getDefaultMessage());
//        });
//
//        String messageError = STR."The payload is not correct. There are missing or incorrect fields: <field> : <error> , ...";
//        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
//        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
