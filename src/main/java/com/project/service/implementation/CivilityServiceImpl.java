package com.project.service.implementation;

import com.project.dto.CivilityDTO;
import com.project.entities.Civility;
import com.project.repository.CivilityRepository;
import com.project.service.CivilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CivilityServiceImpl implements CivilityService {

    private final CivilityRepository civilityRepository;

    public Civility getCivilityById(Long civilityId) {
        return civilityRepository.getReferenceById(civilityId);
    }

    @Override
    public CivilityDTO toDto(Civility civility) {
        return CivilityDTO.builder()
                .civilityId(civility.getCivilityId())
                .libelle(civility.getLibelle().name())
                .build();
    }
}
