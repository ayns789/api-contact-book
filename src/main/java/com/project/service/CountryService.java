package com.project.service;

import com.project.domain.dto.CountryDTO;
import com.project.domain.entities.Country;
import com.project.domain.enums.CountryEnum;

public interface CountryService {
    Country getCountryById(Long countryId);

    CountryDTO toDto(Country country);

    CountryDTO findByLibelle(CountryEnum countryEnum);
}
