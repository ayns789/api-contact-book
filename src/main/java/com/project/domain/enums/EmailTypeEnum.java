package com.project.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum EmailTypeEnum {

    PERSONAL("Personal"),
    PROFESSIONAL("Professional");

    private final String value;

    public static EmailTypeEnum getValue(String value) {
        return Arrays.stream(EmailTypeEnum.values())
                .filter(emailTypeEnum -> emailTypeEnum.value.equals(value))
                .findFirst()
                .orElse(EmailTypeEnum.PERSONAL);
    }
}
