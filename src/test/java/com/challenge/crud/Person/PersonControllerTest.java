package com.challenge.crud.Person;

import com.challenge.crud.person.PersonController;
import com.challenge.crud.person.PersonService;
import com.challenge.crud.person.PersonMapper;
import com.challenge.crud.person.Person;
import com.challenge.crud.person.PersonDTO;
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

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonController personController;

    public PersonControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersonsReturnsOk() {
        Person person = new Person();
        PersonDTO personDTO = new PersonDTO();

        when(personService.getAllPersons()).thenReturn(Collections.singletonList(person));
        when(personMapper.toDTO(person)).thenReturn(personDTO);

        ResponseEntity<List<PersonDTO>> response = personController.getAllPersons();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(personDTO, response.getBody().get(0));
    }

    @Test
    void testGetPersonById() {
        Person person = new Person();
        PersonDTO personDTO = new PersonDTO();

        when(personService.getPersonById(1L)).thenReturn(person);
        when(personMapper.toDTO(person)).thenReturn(personDTO);

        ResponseEntity<PersonDTO> response = personController.getPersonById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDTO, response.getBody());
    }

    @Test
    void testCreatePerson() {
        PersonDTO personDTO = new PersonDTO();
        Person person = new Person();
        Person createdPerson = new Person();
        PersonDTO createdPersonDTO = new PersonDTO();

        when(personMapper.toEntity(personDTO)).thenReturn(person);
        when(personService.createPerson(person)).thenReturn(createdPerson);
        when(personMapper.toDTO(createdPerson)).thenReturn(createdPersonDTO);

        ResponseEntity<PersonDTO> response = personController.createPerson(personDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPersonDTO, response.getBody());
    }

    @Test
    void testUpdatePerson() {
        PersonDTO personDTO = new PersonDTO();
        Person person = new Person();
        Person updatedPerson = new Person();
        PersonDTO updatedPersonDTO = new PersonDTO();

        when(personMapper.toEntity(personDTO)).thenReturn(person);
        when(personService.updatePerson(person)).thenReturn(updatedPerson);
        when(personMapper.toDTO(updatedPerson)).thenReturn(updatedPersonDTO);

        ResponseEntity<PersonDTO> response = personController.updatePerson(personDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPersonDTO, response.getBody());
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personService).deletePerson(1L);

        ResponseEntity<Void> response = personController.deletePerson(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
