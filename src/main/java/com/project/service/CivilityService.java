package com.project.service;

import com.project.domain.dto.CivilityDTO;
import com.project.domain.entities.Civility;

public interface CivilityService {

    Civility getCivilityById(Long civilityId);

    CivilityDTO toDto(Civility civility);
}
