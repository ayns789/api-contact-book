package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class PhoneDTO {

    private Long phoneId;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "'libelle' from phone must be under 50 characters")
    private String libelle;

    private String type;
}
