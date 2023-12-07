package com.project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactDTO {

    private Long contactId;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "'firstname' of contact must be under 50 characters")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "'lastName' of contact must be under 50 characters")
    private String lastName;

    @Valid
    @NotNull
    private CivilityDTO civility;

    @Valid
    private List<EmailDTO> emails;

    @Valid
    @NotNull
    private List<PhoneDTO> phones;

    @Valid
    private List<AddressDTO> addresses;
}
