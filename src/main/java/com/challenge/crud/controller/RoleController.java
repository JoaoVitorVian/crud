package com.challenge.crud.controller;

import com.challenge.crud.dto.EstablishmentDTO;
import com.challenge.crud.dto.RoleDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.service.RoleService;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles")
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRoles() {
        try {
            List<RoleDTO> roleDTOs = roleService.getAllRoles();
            return new ResponseEntity<>(new ApiResponse<>(roleDTOs), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            ApiResponse<List<RoleDTO>> errorResponse = new ApiResponse<>(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ApiResponse<List<RoleDTO>> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a role by ID", description = "Retrieve a role by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDTO>> getRoleById(
            @Parameter(description = "ID of the role to be retrieved") @PathVariable Long id) {
        try {
            RoleDTO roleDTO = roleService.getRoleById(id);
            return new ResponseEntity<>(new ApiResponse<>(roleDTO), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            ApiResponse<RoleDTO> errorResponse = new ApiResponse<>(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ApiResponse<RoleDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new role", description = "Create a new role")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RoleDTO>> createRole(
            @Parameter(description = "Role object to be created") @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO createdRoleDTO = roleService.createRole(roleDTO);
            return new ResponseEntity<>(new ApiResponse<>(createdRoleDTO), HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            ApiResponse<RoleDTO> errorResponse = new ApiResponse<>(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ApiResponse<RoleDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a role", description = "Update an existing role")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<RoleDTO>> updateRole(
            @Parameter(description = "Updated role object with ID") @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO updatedRoleDTO = roleService.updateRole(roleDTO);
            return new ResponseEntity<>(new ApiResponse<>(updatedRoleDTO), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            ApiResponse<RoleDTO> errorResponse = new ApiResponse<>(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ApiResponse<RoleDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a role", description = "Delete a role by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(
            @Parameter(description = "ID of the role to be deleted") @PathVariable Long id) {
        try {
            roleService.deleteRole(id);
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
