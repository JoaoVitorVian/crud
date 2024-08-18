package com.challenge.crud.Person;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.challenge.crud.controller.ApiResponse;
import com.challenge.crud.controller.PersonController;
import com.challenge.crud.dto.Person.PersonCreateDTO;
import com.challenge.crud.dto.Person.PersonDTO;
import com.challenge.crud.service.PersonService;

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
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(personDTO, response.getBody().getData().get(0));
    }

    @Test
    void testGetPersonById() {
        PersonDTO personDTO = new PersonDTO();

        when(personService.getPersonById(1L)).thenReturn(personDTO);

        ResponseEntity<ApiResponse<PersonDTO>> response = personController.getPersonById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(personDTO, response.getBody().getData());
    }

    @Test
    void testCreatePerson() {
        PersonCreateDTO personDTO = new PersonCreateDTO();
        PersonCreateDTO createdPersonDTO = new PersonCreateDTO();

        when(personService.createPerson(personDTO)).thenReturn(createdPersonDTO);

        ResponseEntity<ApiResponse<PersonCreateDTO>> response = personController.createPerson(personDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdPersonDTO, response.getBody().getData());
    }

    @Test
    void testUpdatePerson() {
        PersonDTO personDTO = new PersonDTO();
        PersonDTO updatedPersonDTO = new PersonDTO();

        when(personService.updatePerson(personDTO)).thenReturn(updatedPersonDTO);

        ResponseEntity<ApiResponse<PersonDTO>> response = personController.updatePerson(personDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedPersonDTO, response.getBody().getData());
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personService).deletePerson(1L);

        ResponseEntity<ApiResponse<Void>> response = personController.deletePerson(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
