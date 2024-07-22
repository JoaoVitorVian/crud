package com.challenge.crud.web.controller;

import com.challenge.crud.domain.model.Role;
import com.challenge.crud.service.RoleService;
import com.challenge.crud.web.dto.RoleDTO;
import com.challenge.crud.web.mapper.RoleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles")
    @GetMapping("/get-all")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {

        List<Role> roles = roleService.getAllRoles();

        List<RoleDTO> roleDTOs = roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(roleDTOs);
    }

    @Operation(summary = "Get a role by ID", description = "Retrieve a role by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(
            @Parameter(description = "ID of the role to be retrieved") @PathVariable Long id) {

        Role role = roleService.getRoleById(id);

        RoleDTO roleDTO = roleMapper.toDTO(role);

        return ResponseEntity.ok(roleDTO);
    }

    @Operation(summary = "Create a new role", description = "Create a new role")
    @PostMapping("/create")
    public ResponseEntity<RoleDTO> createRole(
            @Parameter(description = "Role object to be created") @RequestBody RoleDTO roleDTO) {

        Role role = roleMapper.toEntity(roleDTO);

        Role createdRole = roleService.createRole(role);

        RoleDTO createdRoleDTO = roleMapper.toDTO(createdRole);

        return ResponseEntity.status(201).body(createdRoleDTO);
    }

    @Operation(summary = "Update a role", description = "Update an existing role")
    @PutMapping("/update")
    public ResponseEntity<RoleDTO> updateRole(
            @Parameter(description = "Updated role object with ID") @RequestBody RoleDTO roleDTO) {

        Role role = roleMapper.toEntity(roleDTO);

        Role updatedRole = roleService.updateRole(role);

        RoleDTO updatedRoleDTO = roleMapper.toDTO(updatedRole);

        return ResponseEntity.ok(updatedRoleDTO);
    }

    @Operation(summary = "Delete a role", description = "Delete a role by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "ID of the role to be deleted") @PathVariable Long id) {

        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }
}
