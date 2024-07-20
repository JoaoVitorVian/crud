package com.challenge.crud.establishment;

import org.springframework.stereotype.Component;

@Component
public class EstablishmentMapper {

    public EstablishmentDTO toDTO(Establishment establishment) {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        establishmentDTO.setId(establishment.getId());
        establishmentDTO.setName(establishment.getName());
        return establishmentDTO;
    }

    public Establishment toEntity(EstablishmentDTO establishmentDTO) {
        Establishment establishment = new Establishment();
        establishment.setId(establishmentDTO.getId());
        establishment.setName(establishmentDTO.getName());
        return establishment;
    }
}
