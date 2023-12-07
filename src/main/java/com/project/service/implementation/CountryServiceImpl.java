package com.project.service.implementation;

import com.project.dto.CountryDTO;
import com.project.entities.Country;
import com.project.repository.CountryRepository;
import com.project.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public Country getCountryById(Long countryId) {
        return countryRepository.getReferenceById(countryId);
    }

    @Override
    public CountryDTO toDto(Country country) {
        return CountryDTO.builder()
                .countryId(country.getCountryId())
                .libelle(country.getLibelle())
                .build();
    }


}
