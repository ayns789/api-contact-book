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
public class AddressDTO {

    private Long addressId;

    @NotNull
    private Integer streetNumber;

    private String streetType;

    @NotNull
    @NotBlank
    @Size(max = 255, message = "'streetName' of address must be under 255 characters")
    private String streetName;

    @NotNull
    @NotBlank
    @Size(max = 255, message = "'cityName' of address must be under 255 characters")
    private String cityName;

    @NotNull
    private Integer postalCode;

    @NotNull
    private CountryDTO country;
}
