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

import com.challenge.crud.dto.Role.RoleCreateDTO;
import com.challenge.crud.dto.Role.RoleDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

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
    public ResponseEntity<ApiResponse<RoleCreateDTO>> createRole(
            @Parameter(description = "Role object to be created") @RequestBody RoleCreateDTO roleDTO) {
        try {
            RoleCreateDTO createdRoleDTO = roleService.createRole(roleDTO);

            ApiResponse<RoleCreateDTO> successResponse = new ApiResponse<>(createdRoleDTO, "Role created successfully");

            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            ApiResponse<RoleCreateDTO> errorResponse = new ApiResponse<>(ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ApiResponse<RoleCreateDTO> errorResponse = new ApiResponse<>("Internal Server Error: " + ex.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a role", description = "Update an existing role")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<RoleDTO>> updateRole(
            @Parameter(description = "Updated role object with ID") @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO updatedRoleDTO = roleService.updateRole(roleDTO);

            ApiResponse<RoleDTO> successResponse = new ApiResponse<>(updatedRoleDTO, "Role updated");

            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
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
