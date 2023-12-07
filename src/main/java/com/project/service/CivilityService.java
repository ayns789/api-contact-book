package com.project.service;

import com.project.dto.CivilityDTO;
import com.project.entities.Civility;

public interface CivilityService {

    Civility getCivilityById(Long civilityId);

    CivilityDTO toDto(Civility civility);
}
