package com.project.dto;


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
public class CountryDTO {

    private Long countryId;

    @NotNull
    @NotBlank
    @Size(max = 100, message = "'libelle' of country must be under 100 characters")
    private String libelle;
}
