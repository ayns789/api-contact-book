package com.project.exceptions;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message, Object body) {
        super(message);
    }
}
