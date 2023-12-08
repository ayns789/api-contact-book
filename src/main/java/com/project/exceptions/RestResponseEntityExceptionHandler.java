package com.project.exceptions;

import com.project.domain.error.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = "App error";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {GenericException.class})
    public ResponseEntity<Object> handleGenericException(WebRequest request) {

        String messageError = "Internal error";
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError bodyOfResponse = new ApiError(messageError, requestPath, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
