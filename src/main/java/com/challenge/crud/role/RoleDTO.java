package com.challenge.crud.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

    private Long id;
    private String name;

    public RoleDTO(Long id, String name) {

    }

    public RoleDTO() {

    }
}
