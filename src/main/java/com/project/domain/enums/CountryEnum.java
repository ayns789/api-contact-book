package com.project.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CountryEnum {

    FRANCE("FRANCE"),
    ANGLETERRE("ANGLETERRE"),
    ESPAGNE("ESPAGNE"),
    ITALIE("ITALIE"),
    BELGIQUE("BELGIQUE"),
    ARGENTINE("ARGENTINE"),
    AUSTRALIE("AUSTRALIE"),
    JAPON("JAPON"),
    RUSSIE("RUSSIE"),
    INDE("INDE"),
    CHINE("CHINE"),
    AUTRE("AUTRE");


    final String value;

    public static CountryEnum getValue(String value) {
        return Arrays.stream(CountryEnum.values())
                .filter(countryEnum -> countryEnum.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(CountryEnum.AUTRE);
    }
}
