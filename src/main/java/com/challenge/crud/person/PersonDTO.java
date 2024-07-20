package com.challenge.crud.person;

import com.challenge.crud.establishment.EstablishmentDTO;
import com.challenge.crud.role.RoleDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonDTO {

    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Invalid CPF format")
    private String cpf;

    private LocalDate birthDate;

    private LocalDate admissionDate;

    private String email;

    private EstablishmentDTO establishment;

    private RoleDTO role;
}
