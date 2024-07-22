package com.challenge.crud.Establishment;

import com.challenge.crud.controller.ApiResponse;
import com.challenge.crud.model.Establishment;
import com.challenge.crud.service.EstablishmentService;
import com.challenge.crud.controller.EstablishmentController;
import com.challenge.crud.dto.EstablishmentDTO;
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

    @InjectMocks
    private EstablishmentController establishmentController;

    public EstablishmentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEstablishments() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        when(establishmentService.getAllEstablishments()).thenReturn(Collections.singletonList(establishmentDTO));

        ResponseEntity<ApiResponse<List<EstablishmentDTO>>> response = establishmentController.getAllEstablishments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody());
        assertEquals(establishmentDTO, response.getBody());
    }

   @Test
    void testGetEstablishmentById() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        when(establishmentService.getEstablishmentById(1L)).thenReturn(establishmentDTO);

        ResponseEntity<ApiResponse<EstablishmentDTO>> response = establishmentController.getEstablishmentById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(establishmentDTO, response.getBody());
    }

    @Test
    void testCreateEstablishment() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        EstablishmentDTO createdEstablishmentDTO = new EstablishmentDTO();

        when(establishmentService.createEstablishment(establishmentDTO)).thenReturn(createdEstablishmentDTO);

        ResponseEntity<ApiResponse<EstablishmentDTO>> response = establishmentController.createEstablishment(establishmentDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdEstablishmentDTO, response.getBody());
    }

    @Test
    void testUpdateEstablishment() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        EstablishmentDTO updatedEstablishmentDTO = new EstablishmentDTO();

        when(establishmentService.updateEstablishment(establishmentDTO)).thenReturn(updatedEstablishmentDTO);

        ResponseEntity<ApiResponse<EstablishmentDTO>> response = establishmentController.updateEstablishment(establishmentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEstablishmentDTO, response.getBody());
    }

    @Test
    void testDeleteEstablishment() {
        doNothing().when(establishmentService).deleteEstablishment(1L);

        ResponseEntity<ApiResponse<Void>> response = establishmentController.deleteEstablishment(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

