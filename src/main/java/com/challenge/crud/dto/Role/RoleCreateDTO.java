package com.challenge.crud.dto.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleCreateDTO {
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public RoleCreateDTO(Long id, String name) {

    }

    public RoleCreateDTO() {

    }
}
