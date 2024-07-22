package com.challenge.crud.controller;

import com.challenge.crud.dto.EstablishmentDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.service.EstablishmentService;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    @Autowired
    EstablishmentService establishmentService;

    @Operation(summary = "Get all establishments", description = "Retrieve a list of all establishments")
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<EstablishmentDTO>>> getAllEstablishments() {
        try {
            List<EstablishmentDTO> establishmentDTOs = establishmentService.getAllEstablishments();

            return new ResponseEntity<>(new ApiResponse<>(establishmentDTOs), HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            ApiResponse<List<EstablishmentDTO>> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ApiResponse<List<EstablishmentDTO>> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get an establishment by ID", description = "Retrieve an establishment by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstablishmentDTO>> getEstablishmentById(
            @Parameter(description = "ID of the establishment to be retrieved") @PathVariable Long id) {
        try {
            EstablishmentDTO establishmentDTO = establishmentService.getEstablishmentById(id);

            return new ResponseEntity<>(new ApiResponse<>(establishmentDTO), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            ApiResponse<EstablishmentDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ApiResponse<EstablishmentDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new establishment", description = "Create a new establishment")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EstablishmentDTO>> createEstablishment(
            @Parameter(description = "Establishment object to be created") @RequestBody EstablishmentDTO establishmentDTO) {
        try {
            EstablishmentDTO createdEstablishmentDTO = establishmentService.createEstablishment(establishmentDTO);

            return new ResponseEntity<>(new ApiResponse<>(createdEstablishmentDTO), HttpStatus.CREATED);
        }  catch (ResourceNotFoundException ex) {
            ApiResponse<EstablishmentDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ApiResponse<EstablishmentDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an establishment", description = "Update an existing establishment")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<EstablishmentDTO>> updateEstablishment(
            @Parameter(description = "Updated establishment object with ID") @RequestBody EstablishmentDTO establishmentDTO) {
        try {
            EstablishmentDTO updatedEstablishmentDTO = establishmentService.updateEstablishment(establishmentDTO);

            return new ResponseEntity<>(new ApiResponse<>(updatedEstablishmentDTO), HttpStatus.OK);
        }  catch (ResourceNotFoundException ex) {
            ApiResponse<EstablishmentDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ApiResponse<EstablishmentDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete an establishment", description = "Delete an establishment by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEstablishment(
            @Parameter(description = "ID of the establishment to be deleted") @PathVariable Long id) {
        try {
            establishmentService.deleteEstablishment(id);

            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            ApiResponse<Void> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }  catch (Exception ex) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
