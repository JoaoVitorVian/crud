package com.challenge.crud.dto.Establishment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstablishmentCreateDTO {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public EstablishmentCreateDTO(Long id, String name) {

    }

    public EstablishmentCreateDTO() {

    }
}
