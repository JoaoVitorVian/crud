package com.challenge.crud.Establishment;

import com.challenge.crud.establishment.Establishment;
import com.challenge.crud.establishment.EstablishmentService;
import com.challenge.crud.establishment.EstablishmentMapper;
import com.challenge.crud.establishment.EstablishmentController;
import com.challenge.crud.establishment.EstablishmentDTO;
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
public class EstablishmentControllerTest {

    @Mock
    private EstablishmentService establishmentService;

    @Mock
    private EstablishmentMapper establishmentMapper;

    @InjectMocks
    private EstablishmentController establishmentController;

    public EstablishmentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEstablishments() {
        Establishment establishment = new Establishment();
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        when(establishmentService.getAllEstablishments()).thenReturn(Collections.singletonList(establishment));
        when(establishmentMapper.toDTO(establishment)).thenReturn(establishmentDTO);

        ResponseEntity<List<EstablishmentDTO>> response = establishmentController.getAllEstablishments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(establishmentDTO, response.getBody().get(0));
    }

    @Test
    void testGetEstablishmentById() {
        Establishment establishment = new Establishment();
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        when(establishmentService.getEstablishmentById(1L)).thenReturn(establishment);
        when(establishmentMapper.toDTO(establishment)).thenReturn(establishmentDTO);

        ResponseEntity<EstablishmentDTO> response = establishmentController.getEstablishmentById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(establishmentDTO, response.getBody());
    }

    @Test
    void testCreateEstablishment() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        Establishment establishment = new Establishment();
        Establishment createdEstablishment = new Establishment();
        EstablishmentDTO createdEstablishmentDTO = new EstablishmentDTO();

        when(establishmentMapper.toEntity(establishmentDTO)).thenReturn(establishment);
        when(establishmentService.createEstablishment(establishment)).thenReturn(createdEstablishment);
        when(establishmentMapper.toDTO(createdEstablishment)).thenReturn(createdEstablishmentDTO);

        ResponseEntity<EstablishmentDTO> response = establishmentController.createEstablishment(establishmentDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdEstablishmentDTO, response.getBody());
    }

    @Test
    void testUpdateEstablishment() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        Establishment establishment = new Establishment();
        Establishment updatedEstablishment = new Establishment();
        EstablishmentDTO updatedEstablishmentDTO = new EstablishmentDTO();

        when(establishmentMapper.toEntity(establishmentDTO)).thenReturn(establishment);
        when(establishmentService.updateEstablishment(establishment)).thenReturn(updatedEstablishment);
        when(establishmentMapper.toDTO(updatedEstablishment)).thenReturn(updatedEstablishmentDTO);

        ResponseEntity<EstablishmentDTO> response = establishmentController.updateEstablishment(establishmentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEstablishmentDTO, response.getBody());
    }

    @Test
    void testDeleteEstablishment() {
        doNothing().when(establishmentService).deleteEstablishment(1L);

        ResponseEntity<Void> response = establishmentController.deleteEstablishment(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

