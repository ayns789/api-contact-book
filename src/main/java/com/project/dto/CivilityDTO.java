package com.project.dto;

import com.project.enums.CivilityEnumType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CivilityDTO {

    private Long civilityId;

    @NotNull
    @Max(value = 20, message = "'libelle' of civility must be under 20 characters")
    @Enumerated(EnumType.STRING)
    private CivilityEnumType libelle;
}
