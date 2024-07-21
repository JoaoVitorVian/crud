package com.challenge.crud.person;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @Operation(summary = "Get all persons", description = "Retrieve a list of all persons")
    @GetMapping("/get-all")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {

        List<Person> persons = personService.getAllPersons();

        List<PersonDTO> personDTOs = persons.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(personDTOs);
    }

    @Operation(summary = "Get a person by ID", description = "Retrieve a person by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(
            @Parameter(description = "ID of the person to be retrieved") @PathVariable Long id) {

        Person person = personService.getPersonById(id);

        PersonDTO personDTO = personMapper.toDTO(person);

        return ResponseEntity.ok(personDTO);
    }

    @Operation(summary = "Create a new person", description = "Create a new person")
    @PostMapping("/create")
    public ResponseEntity<PersonDTO> createPerson(
            @Parameter(description = "Person object to be created") @RequestBody PersonDTO personDTO) {

        Person person = personMapper.toEntity(personDTO);

        Person createdPerson = personService.createPerson(person);

        PersonDTO createdPersonDTO = personMapper.toDTO(createdPerson);

        return ResponseEntity.status(201).body(createdPersonDTO);
    }

    @Operation(summary = "Update a person", description = "Update an existing person")
    @PutMapping("/update")
    public ResponseEntity<PersonDTO> updatePerson(
            @Parameter(description = "Updated person object with ID") @RequestBody PersonDTO personDTO) {

        Person person = personMapper.toEntity(personDTO);

        Person updatedPerson = personService.updatePerson(person);

        PersonDTO updatedPersonDTO = personMapper.toDTO(updatedPerson);

        return ResponseEntity.ok(updatedPersonDTO);
    }

    @Operation(summary = "Delete a person", description = "Delete a person by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "ID of the person to be deleted") @PathVariable Long id) {

        personService.deletePerson(id);

        return ResponseEntity.noContent().build();
    }
}
