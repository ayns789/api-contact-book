package com.project.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum CivilityEnumType {

    MONSIEUR("Monsieur"),
    MADAME("Madame"),
    MADEMOISELLE("Mademoiselle"),
    NON_BINAIRE("non_binaire"),
    AUTRE("autre");

    final String value;

    public CivilityEnumType getvalue(String value) {
        return Arrays.stream(CivilityEnumType.values())
                .filter(civilityEnumType -> civilityEnumType.value.equals(value))
                .findFirst()
                .orElse(CivilityEnumType.AUTRE);
    }
}
