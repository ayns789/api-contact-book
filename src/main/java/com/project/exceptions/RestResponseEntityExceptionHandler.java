package com.project.exceptions;

import com.project.domain.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GenericException.class, Exception.class})
    public ResponseEntity<Object> handleGenericException(WebRequest request) {

        String messageError = "Internal error";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CountryNotFoundException.class})
    public ResponseEntity<Object> handleCountryNotFoundException(CountryNotFoundException ex, WebRequest request) {

        String messageError = STR."The country with id : \{ex.getCountryId()} does not exist";
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

        String messageError = STR."The civility with id : \{ex.getCivilityId()} does not exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {StreetTypeEnumNotFoundException.class})
    public ResponseEntity<Object> handleStreetTypeEnumNotFoundException(StreetTypeEnumNotFoundException ex, WebRequest request) {

        String messageError = STR."The street type with value : '\{ex.getStreetType()}' does not exist";
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

    @ExceptionHandler(value = {IdNotFoundException.class})
    public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException ex, WebRequest request) {

        String messageError = "This contact does not exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {LastnameNotFoundException.class})
    public ResponseEntity<Object> handleLastnameNotFoundException(LastnameNotFoundException ex, WebRequest request) {

        String messageError = "This last name does not exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {FirstnameNotFoundException.class})
    public ResponseEntity<Object> handleFirstnameNotFoundException(FirstnameNotFoundException ex, WebRequest request) {

        String messageError = "This first name does not exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PhoneNotFoundException.class})
    public ResponseEntity<Object> handlePhoneNotFoundException(PhoneNotFoundException ex, WebRequest request) {

        String messageError = "This phone does not exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ContactNotFoundException.class})
    public ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException ex, WebRequest request) {

        String messageError = "This contact does not exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ContactNotUpdatedException.class})
    public ResponseEntity<Object> handleContactNotUpdatedException(ContactNotUpdatedException ex, WebRequest request) {

        String messageError = "The contact could not be updated ";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ContactNotDeletedException.class})
    public ResponseEntity<Object> handleContactNotDeletedException(ContactNotDeletedException ex, WebRequest request) {

        String messageError = "The contact could not be deleted ";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {EmailNotDeletedException.class})
    public ResponseEntity<Object> handleEmailNotDeletedException(EmailNotDeletedException ex, WebRequest request) {

        String messageError = "The email could not be deleted ";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {PhoneNotDeletedException.class})
    public ResponseEntity<Object> handlePhoneNotDeletedException(PhoneNotDeletedException ex, WebRequest request) {

        String messageError = "The phone could not be deleted ";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AddressNotDeletedException.class})
    public ResponseEntity<Object> handleAddressNotDeletedException(AddressNotDeletedException ex, WebRequest request) {

        String messageError = "The address could not be deleted ";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {FileExcelNotGeneratedException.class})
    public ResponseEntity<Object> handleFileExcelNotGeneratedException(FileExcelNotGeneratedException ex, WebRequest request) {

        String messageError = "Error during creation Excel file operation ";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {FileErrorExtensionException.class})
    public ResponseEntity<Object> handleFileErrorExtensionException(FileErrorExtensionException ex, WebRequest request) {

        String messageError = "This file does not have extension : 'xlsx'";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ContactAlreadyExistException.class})
    public ResponseEntity<Object> handleContactAlreadyExistException(ContactAlreadyExistException ex, WebRequest request) {

        String messageError = "This contact already exist";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }


}
