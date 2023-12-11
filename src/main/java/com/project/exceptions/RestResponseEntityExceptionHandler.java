package com.project.exceptions;

import com.project.domain.error.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String messageError = STR. "The payload is not valid ('\{ ex.getMessage() }')" ;
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {GenericException.class, Exception.class})
    public ResponseEntity<Object> handleGenericException(WebRequest request) {

        String messageError = "Internal error";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CountryNotFoundException.class})
    public ResponseEntity<Object> handleCountryNotFoundException(CountryNotFoundException ex, WebRequest request) {

        String messageError = STR. "The country with id : \{ ex.getCountryId() } does not exist" ;
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AddressNotSavedException.class})
    public ResponseEntity<Object> handleAddressNotSavedException(AddressNotSavedException ex, WebRequest request) {

        String messageError = "The address could not be saved";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CivilityNotFoundException.class})
    public ResponseEntity<Object> handleCivilityNotFoundException(CivilityNotFoundException ex, WebRequest request) {

        String messageError = STR. "The civility with id : \{ ex.getCivilityId() } does not exist" ;
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {StreetTypeEnumNotFoundException.class})
    public ResponseEntity<Object> handleStreetTypeEnumNotFoundException(StreetTypeEnumNotFoundException ex, WebRequest request) {

        String messageError = STR. "The street type with value : '\{ ex.getStreetType() }' does not exist" ;
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EmailNotSavedException.class})
    public ResponseEntity<Object> handleEmailNotSavedException(EmailNotSavedException ex, WebRequest request) {

        String messageError = "The email could not be saved";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PhoneNotSavedException.class})
    public ResponseEntity<Object> handlePhoneNotSavedException(PhoneNotSavedException ex, WebRequest request) {

        String messageError = "The phone could not be saved";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ContactNotSavedException.class})
    public ResponseEntity<Object> handleContactNotSavedException(ContactNotSavedException ex, WebRequest request) {

        String messageError = "The contact could not be saved";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
