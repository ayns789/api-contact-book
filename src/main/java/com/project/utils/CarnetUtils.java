package com.project.utils;

import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;

import java.util.Optional;

/**
 * @author edem ANINI-TOULASSI
 * @created Tuesday/December/2023
 * @project api-contact-book
 */
@UtilityClass
public class CarnetUtils {

    /**
     * Returns the field path of the given FieldError.
     *
     * @param error the FieldError containing the field path
     * @return the field path extracted from the FieldError
     */
    public static String getFieldPath(FieldError error) {
        return Optional.of(error.getField())
            .filter(field -> field.contains("."))
            .map(field -> field.substring(field.lastIndexOf(".") + 1))
            .orElse(error.getField());
    }
}
