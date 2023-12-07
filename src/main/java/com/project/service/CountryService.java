package com.project.service;

import com.project.dto.CountryDTO;
import com.project.entities.Country;

public interface CountryService {
    Country getCountryById(Long countryId);

    CountryDTO toDto(Country country);
}
