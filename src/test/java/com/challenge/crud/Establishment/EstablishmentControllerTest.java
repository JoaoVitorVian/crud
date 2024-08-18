package com.challenge.crud.Establishment;

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
import com.challenge.crud.controller.EstablishmentController;
import com.challenge.crud.dto.Establishment.EstablishmentCreateDTO;
import com.challenge.crud.dto.Establishment.EstablishmentDTO;
import com.challenge.crud.service.EstablishmentService;

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
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(establishmentDTO, response.getBody().getData().get(0));
    }

    @Test
    void testGetEstablishmentById() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        when(establishmentService.getEstablishmentById(1L)).thenReturn(establishmentDTO);

        ResponseEntity<ApiResponse<EstablishmentDTO>> response = establishmentController.getEstablishmentById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(establishmentDTO, response.getBody().getData());
    }

    @Test
    void testCreateEstablishment() {
        EstablishmentCreateDTO establishmentDTO = new EstablishmentCreateDTO();
        EstablishmentCreateDTO createdEstablishmentDTO = new EstablishmentCreateDTO();

        when(establishmentService.createEstablishment(establishmentDTO)).thenReturn(createdEstablishmentDTO);

        ResponseEntity<ApiResponse<EstablishmentCreateDTO>> response = establishmentController.createEstablishment(establishmentDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdEstablishmentDTO, response.getBody().getData());
    }

    @Test
    void testUpdateEstablishment() {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        EstablishmentDTO updatedEstablishmentDTO = new EstablishmentDTO();

        when(establishmentService.updateEstablishment(establishmentDTO)).thenReturn(updatedEstablishmentDTO);

        ResponseEntity<ApiResponse<EstablishmentDTO>> response = establishmentController.updateEstablishment(establishmentDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedEstablishmentDTO, response.getBody().getData());
    }

    @Test
    void testDeleteEstablishment() {
        doNothing().when(establishmentService).deleteEstablishment(1L);
    
        ResponseEntity<ApiResponse<Void>> response = establishmentController.deleteEstablishment(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
