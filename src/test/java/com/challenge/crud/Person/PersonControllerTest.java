package com.challenge.crud.Person;

import com.challenge.crud.controller.ApiResponse;
import com.challenge.crud.controller.PersonController;
import com.challenge.crud.service.PersonService;
import com.challenge.crud.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    public PersonControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersonsReturnsOk() {
        PersonDTO personDTO = new PersonDTO();

        when(personService.getAllPersons()).thenReturn(Collections.singletonList(personDTO));

        ResponseEntity<ApiResponse<List<PersonDTO>>> response = personController.getAllPersons();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody());
        assertEquals(personDTO, response.getBody());
    }

   @Test
    void testGetPersonById() {
        PersonDTO personDTO = new PersonDTO();

        when(personService.getPersonById(1L)).thenReturn(personDTO);

        ResponseEntity<ApiResponse<PersonDTO>> response = personController.getPersonById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDTO, response.getBody());
    }


   @Test
    void testCreatePerson() {
        PersonDTO personDTO = new PersonDTO();
        PersonDTO createdPersonDTO = new PersonDTO();

        when(personService.createPerson(personDTO)).thenReturn(createdPersonDTO);

        ResponseEntity<ApiResponse<PersonDTO>> response = personController.createPerson(personDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPersonDTO, response.getBody());
    }


    @Test
    void testUpdatePerson() {
        PersonDTO personDTO = new PersonDTO();
        PersonDTO updatedPersonDTO = new PersonDTO();

        when(personService.updatePerson(personDTO)).thenReturn(updatedPersonDTO);

        ResponseEntity<ApiResponse<PersonDTO>> response = personController.updatePerson(personDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPersonDTO, response.getBody());
    }


   @Test
    void testDeletePerson() {
        doNothing().when(personService).deletePerson(1L);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
