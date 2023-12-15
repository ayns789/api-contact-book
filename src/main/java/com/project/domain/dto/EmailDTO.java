package com.project.domain.dto;

import jakarta.validation.constraints.Email;
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
public class EmailDTO {

    private Long emailId;

    @NotNull
    @NotBlank
    @Email(message = "The email could not be saved")
    @Size(max = 255, message = "'libelle' from email must be under 255 characters")
    private String libelle;

    private String type;
}
