package com.challenge.crud.person;

import com.challenge.crud.establishment.Establishment;
import com.challenge.crud.establishment.EstablishmentDTO;
import com.challenge.crud.role.Role;
import com.challenge.crud.role.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonDTO toDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setCpf(person.getCpf());
        personDTO.setBirthDate(person.getBirthDate());
        personDTO.setAdmissionDate(person.getAdmissionDate());
        personDTO.setEmail(person.getEmail());

        if (person.getEstablishment() != null) {
            EstablishmentDTO establishmentDTO = new EstablishmentDTO();
            establishmentDTO.setId(person.getEstablishment().getId());
            establishmentDTO.setName(person.getEstablishment().getName());
            personDTO.setEstablishment(establishmentDTO);
        } else {
            personDTO.setEstablishment(null);
        }

        if (person.getRole() != null) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(person.getRole().getId());
            roleDTO.setName(person.getRole().getName());
            personDTO.setRole(roleDTO);
        } else {
            personDTO.setRole(null);
        }

        return personDTO;
    }

    public Person toEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        person.setCpf(personDTO.getCpf());
        person.setBirthDate(personDTO.getBirthDate());
        person.setAdmissionDate(personDTO.getAdmissionDate());
        person.setEmail(personDTO.getEmail());

        if (personDTO.getEstablishment() != null) {
            Establishment establishment = new Establishment();
            establishment.setId(personDTO.getEstablishment().getId());
            person.setEstablishment(establishment);
        } else {
            person.setEstablishment(null);
        }

        if (personDTO.getRole() != null) {
            Role role = new Role();
            role.setId(personDTO.getRole().getId());
            person.setRole(role);
        } else {
            person.setRole(null);
        }

        return person;
    }
}


