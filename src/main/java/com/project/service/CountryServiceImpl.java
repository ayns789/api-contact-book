package com.project.service;

import com.project.entities.Country;
import com.project.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country getCountryById(Long countryId) {
        Country country = countryRepository.getReferenceById(countryId);
        return country;
    }


}
