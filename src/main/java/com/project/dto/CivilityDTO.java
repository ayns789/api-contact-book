package com.project.dto;

import jakarta.validation.constraints.Max;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class CivilityDTO {

    private Long civilityId;

    @Max(value = 20, message = "'libelle' of civility must be under 20 characters")
    private String libelle;
}
