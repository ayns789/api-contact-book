package com.project.dto;

import com.project.enums.PhoneTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PhoneDTO {

    private Long phoneId;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "'libelle' from phone must be under 50 characters")
    private String libelle;

    @Enumerated(EnumType.STRING)
    private PhoneTypeEnum type;
}
