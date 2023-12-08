package com.project.domain.error;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * @author edem ANINI-TOULASSI
 * @created Friday/December/2023
 * @project api-contact-book
 */
@Getter
@Setter
public class ApiError {

    private String message;
    private Instant timestamp;
    private String path;
    private Integer status;

    public ApiError(String message, String path, Integer status) {
        this.timestamp = Instant.now();
        this.message = message;
        this.path = path;
        this.status = status;
    }
}
