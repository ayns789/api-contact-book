package com.project.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum StreetTypeEnum {

    STREET("Street"),
    QUAY("Quay"),
    AVENUE("Avenue");

    private final String value;

    public static StreetTypeEnum getValue(String value) {
        return Arrays.stream(StreetTypeEnum.values())
                .filter(streetTypeEnum -> streetTypeEnum.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new); // todo : create a custom exception
    }
}
