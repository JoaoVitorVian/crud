package com.challenge.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crud.dto.Person.PersonCreateDTO;
import com.challenge.crud.dto.Person.PersonDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<ApiResponse<PersonCreateDTO>> createPerson(
            @Parameter(description = "Person object to be created") @RequestBody PersonCreateDTO personDTO) {
        try {
            PersonCreateDTO createdPerson = personService.createPerson(personDTO);

            ApiResponse<PersonCreateDTO> successResponse = new ApiResponse<>(createdPerson, "Person created successfully");

            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        }catch (ResourceNotFoundException ex) {

            ApiResponse<PersonCreateDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {

            ApiResponse<PersonCreateDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a person", description = "Update an existing person")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<PersonDTO>> updatePerson(
            @Parameter(description = "Updated person object with ID") @RequestBody PersonDTO personDTO) {
        try {

            PersonDTO updatedPerson = personService.updatePerson(personDTO);

            ApiResponse<PersonDTO> successResponse = new ApiResponse<>(updatedPerson, "Person updated");

            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
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

