package com.project.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author edem ANINI-TOULASSI
 * @created Friday/December/2023
 * @project api-contact-book
 */
@RequiredArgsConstructor
@Getter
public class StreetTypeEnumNotFoundException extends RuntimeException {
    private final String streetType;
}
