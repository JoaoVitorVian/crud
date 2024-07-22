package com.challenge.crud.controller;

import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.service.PersonService;
import com.challenge.crud.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;

import java.util.List;
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @Operation(summary = "Get all persons", description = "Retrieve a list of all persons")
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<PersonDTO>>> getAllPersons() {
        try {
            List<PersonDTO> personDTOs = personService.getAllPersons();

            return new ResponseEntity<>(new ApiResponse<>(personDTOs), HttpStatus.OK);
        }catch (ResourceNotFoundException ex) {

            ApiResponse<List<PersonDTO>> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {

            ApiResponse<List<PersonDTO>> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a person by ID", description = "Retrieve a person by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonDTO>> getPersonById(
            @Parameter(description = "ID of the person to be retrieved") @PathVariable Long id) {
        try {
            PersonDTO person = personService.getPersonById(id);

            return new ResponseEntity<>(new ApiResponse<>(person), HttpStatus.OK);

        }catch (ResourceNotFoundException ex) {

            ApiResponse<PersonDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }catch (Exception ex) {

            ApiResponse<PersonDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new person", description = "Create a new person")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PersonDTO>> createPerson(
            @Parameter(description = "Person object to be created") @RequestBody PersonDTO personDTO) {
        try {
            PersonDTO createdPerson = personService.createPerson(personDTO);

            return new ResponseEntity<>(new ApiResponse<>(createdPerson), HttpStatus.CREATED);
        }catch (ResourceNotFoundException ex) {

            ApiResponse<PersonDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {

            ApiResponse<PersonDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a person", description = "Update an existing person")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<PersonDTO>> updatePerson(
            @Parameter(description = "Updated person object with ID") @RequestBody PersonDTO personDTO) {
        try {

            PersonDTO updatedPerson = personService.updatePerson(personDTO);

            return new ResponseEntity<>(new ApiResponse<>(updatedPerson), HttpStatus.OK);
        }catch (ResourceNotFoundException ex) {

            ApiResponse<PersonDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {

            ApiResponse<PersonDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a person", description = "Delete a person by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePerson(
            @Parameter(description = "ID of the person to be deleted") @PathVariable Long id) {
        try {
            personService.deletePerson(id);

            return ResponseEntity.noContent().build();

        }catch (ResourceNotFoundException ex) {

            ApiResponse<Void> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {

            ApiResponse<Void> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

