package com.challenge.crud.establishment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstablishmentDTO {

    private Long id;
    private String name;

    public EstablishmentDTO(Long id, String name) {

    }

    public EstablishmentDTO() {

    }
}
