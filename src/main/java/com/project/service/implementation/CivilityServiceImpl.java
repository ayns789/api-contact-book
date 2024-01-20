package com.project.service.implementation;

import com.project.domain.dto.CivilityDTO;
import com.project.domain.entities.Civility;
import com.project.domain.enums.CivilityEnumType;
import com.project.exceptions.CivilityNotFoundException;
import com.project.repository.CivilityRepository;
import com.project.service.CivilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CivilityServiceImpl implements CivilityService {

    private final CivilityRepository civilityRepository;

    public Civility getCivilityById(Long civilityId) {
        return civilityRepository.findById(civilityId)
                .orElseThrow(() -> new CivilityNotFoundException(civilityId));
    }

    @Override
    public CivilityDTO findByLibelle(CivilityEnumType civilityEnumType) {
        Civility civility = civilityRepository.findByLibelle(civilityEnumType);
        // to Dto
        return toDto(civility);
    }

    @Override
    public CivilityDTO toDto(Civility civility) {
        return CivilityDTO.builder()
                .civilityId(civility.getCivilityId())
                .libelle(civility.getLibelle().name())
                .build();
    }

    @Override
    public Civility toEntity(CivilityDTO civilityDTO) {
        Civility civility = new Civility();
        civility.setCivilityId(civilityDTO.getCivilityId());
        civility.setLibelle(CivilityEnumType.valueOf(civilityDTO.getLibelle()));
        return civility;
    }
}
