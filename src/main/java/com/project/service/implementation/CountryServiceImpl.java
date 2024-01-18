package com.project.service.implementation;

import com.project.domain.dto.CountryDTO;
import com.project.domain.entities.Country;
import com.project.domain.enums.CountryEnum;
import com.project.exceptions.CountryNotFoundException;
import com.project.repository.CountryRepository;
import com.project.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public Country getCountryById(Long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }

    @Override
    public CountryDTO findByLibelle(CountryEnum countryEnum) {
        Country country = countryRepository.findByLibelle(countryEnum);
        // to Dto
        return toDto(country);
    }

    @Override
    public CountryDTO toDto(Country country) {
        return CountryDTO.builder()
                .countryId(country.getCountryId())
                .libelle(country.getLibelle())
                .build();
    }


}
