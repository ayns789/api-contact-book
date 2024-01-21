package com.project.service;

import com.project.domain.dto.CivilityDTO;
import com.project.domain.dto.ContactDTO;
import com.project.domain.entities.Civility;
import com.project.domain.enums.CivilityEnumType;

public interface CivilityService {

    Civility getCivilityById(Long civilityId);

    CivilityDTO toDto(Civility civility);

    Civility toEntity(CivilityDTO civilityDTO);

    CivilityDTO findByLibelle(CivilityEnumType civilityEnumType);

    void handleCivilityForImportFile(ContactDTO contactDTO, String currentCellValue);
}
