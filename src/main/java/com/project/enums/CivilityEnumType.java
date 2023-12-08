package com.project.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum CivilityEnumType {

    MONSIEUR("MONSIEUR"),
    MADAME("MADAME"),
    MADEMOISELLE("MADEMOISELLE"),
    NON_BINAIRE("NON_BINAIRE"),
    AUTRE("AUTRE");

    final String value;

    public CivilityEnumType getvalue(String value) {
        return Arrays.stream(CivilityEnumType.values())
                .filter(civilityEnumType -> civilityEnumType.value.equals(value))
                .findFirst()
                .orElse(CivilityEnumType.AUTRE);
    }
}
