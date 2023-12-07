package com.project.dto;

import com.project.enums.EmailTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EmailDTO {

    private Long emailId;

    @NotNull
    @NotBlank
    @Email
    @Size(max = 255, message = "'libelle' from email must be under 255 characters")
    private String libelle;

    @Enumerated(EnumType.STRING)
    private EmailTypeEnum type;
}
