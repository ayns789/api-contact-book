package com.project.exceptions;

public class InvalidSystemException extends RuntimeException {

    public InvalidSystemException(String message, Object body) {
        super(message);
    }
}
