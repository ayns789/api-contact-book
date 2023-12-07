package com.project.service.implementation;

import com.project.entities.Civility;
import com.project.repository.CivilityRepository;
import org.springframework.stereotype.Service;

@Service
public class CivilityServiceImpl {

    private final CivilityRepository civilityRepository;

    public CivilityServiceImpl(CivilityRepository civilityRepository) {
        this.civilityRepository = civilityRepository;
    }

    public Civility getCivilityById(Long civilityId) {
        Civility civility = civilityRepository.getReferenceById(civilityId);
        return civility;
    }
}
