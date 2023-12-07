package com.project.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum PhoneTypeEnum {

    PERSONAL("Personal"),
    PROFESSIONAL("Professional");

    private final String value;

    public static PhoneTypeEnum getValue(String value) {
        return Arrays.stream(PhoneTypeEnum.values())
                .filter(phoneTypeEnum -> phoneTypeEnum.value.equals(value))
                .findFirst()
                .orElse(PhoneTypeEnum.PERSONAL);
    }
}
