package com.project.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CivilityEnumType {

    MONSIEUR("MONSIEUR"),
    MADAME("MADAME"),
    MADEMOISELLE("MADEMOISELLE"),
    NON_BINAIRE("NON_BINAIRE"),
    AUTRE("AUTRE");

    final String value;

    public static CivilityEnumType getValue(String value) {
        
        return Arrays.stream(CivilityEnumType.values())
                .filter(civilityEnumType -> civilityEnumType.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(CivilityEnumType.AUTRE);
    }

}
