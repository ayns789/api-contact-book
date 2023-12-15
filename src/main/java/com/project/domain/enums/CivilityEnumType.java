package com.project.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CivilityEnumType {

    MONSIEUR("MONSIEUR"),
    MADAME("MADAME"),
    MADEMOISELLE("MADEMOISELLE"),
    NON_BINAIRE("NON_BINAIRE"),
    AUTRE("AUTRE");

    final String value;
}
