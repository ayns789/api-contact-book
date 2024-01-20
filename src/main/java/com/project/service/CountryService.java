package com.project.service;

import com.project.domain.dto.CountryDTO;
import com.project.domain.entities.Country;

public interface CountryService {
    Country getCountryById(Long countryId);

    CountryDTO toDto(Country country);

    CountryDTO findByLibelle(String libelle);
}
