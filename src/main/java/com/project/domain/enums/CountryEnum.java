package com.project.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CountryEnum {

    FRANCE("France"),
    ANGLETERRE("Angleterre"),
    ESPAGNE("Espagne"),
    ITALIE("Italie"),
    BELGIQUE("Belgique"),
    ARGENTINE("Argentine"),
    AUSTRALIE("Australie"),
    JAPON("Japon"),
    RUSSIE("Russie"),
    INDE("Inde"),
    CHINE("Chine"),
    AUTRE("Autre");


    final String value;

    public static CountryEnum getValue(String value) {
        return Arrays.stream(CountryEnum.values())
                .filter(countryEnum -> countryEnum.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(CountryEnum.AUTRE);
    }
}
