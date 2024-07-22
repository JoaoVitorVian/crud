package com.challenge.crud.web.controller;

import com.challenge.crud.domain.model.Establishment;
import com.challenge.crud.service.EstablishmentService;
import com.challenge.crud.web.dto.EstablishmentDTO;
import com.challenge.crud.web.mapper.EstablishmentMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;
    private final EstablishmentMapper establishmentMapper;

    public EstablishmentController(EstablishmentService establishmentService, EstablishmentMapper establishmentMapper) {
        this.establishmentService = establishmentService;
        this.establishmentMapper = establishmentMapper;
    }

    @Operation(summary = "Get all establishments", description = "Retrieve a list of all establishments")
    @GetMapping("/get-all")
    public ResponseEntity<List<EstablishmentDTO>> getAllEstablishments() {

        List<Establishment> establishments = establishmentService.getAllEstablishments();

        List<EstablishmentDTO> establishmentDTOs = establishments.stream()
                .map(establishmentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(establishmentDTOs);
    }

    @Operation(summary = "Get an establishment by ID", description = "Retrieve an establishment by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<EstablishmentDTO> getEstablishmentById(
            @Parameter(description = "ID of the establishment to be retrieved") @PathVariable Long id) {

        Establishment establishment = establishmentService.getEstablishmentById(id);

        EstablishmentDTO establishmentDTO = establishmentMapper.toDTO(establishment);
        return ResponseEntity.ok(establishmentDTO);
    }

    @Operation(summary = "Create a new establishment", description = "Create a new establishment")
    @PostMapping("/create")
    public ResponseEntity<EstablishmentDTO> createEstablishment(
            @Parameter(description = "Establishment object to be created") @RequestBody EstablishmentDTO establishmentDTO) {
        Establishment establishment = establishmentMapper.toEntity(establishmentDTO);

        Establishment createdEstablishment = establishmentService.createEstablishment(establishment);

        EstablishmentDTO createdEstablishmentDTO = establishmentMapper.toDTO(createdEstablishment);
        return ResponseEntity.status(201).body(createdEstablishmentDTO);
    }

    @Operation(summary = "Update an establishment", description = "Update an existing establishment")
    @PutMapping("/update")
    public ResponseEntity<EstablishmentDTO> updateEstablishment(
            @Parameter(description = "Updated establishment object with ID") @RequestBody EstablishmentDTO establishmentDTO) {
        Establishment establishment = establishmentMapper.toEntity(establishmentDTO);

        Establishment updatedEstablishment = establishmentService.updateEstablishment(establishment);

        EstablishmentDTO updatedEstablishmentDTO = establishmentMapper.toDTO(updatedEstablishment);
        return ResponseEntity.ok(updatedEstablishmentDTO);
    }

    @Operation(summary = "Delete an establishment", description = "Delete an establishment by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstablishment(
            @Parameter(description = "ID of the establishment to be deleted") @PathVariable Long id) {

        establishmentService.deleteEstablishment(id);

        return ResponseEntity.noContent().build();
    }
}
