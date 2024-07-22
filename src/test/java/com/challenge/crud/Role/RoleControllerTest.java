package com.challenge.crud.Role;

import com.challenge.crud.controller.ApiResponse;
import com.challenge.crud.service.RoleService;
import com.challenge.crud.controller.RoleController;
import com.challenge.crud.dto.RoleDTO;
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
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    public RoleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        RoleDTO roleDTO = new RoleDTO();

        when(roleService.getAllRoles()).thenReturn(Collections.singletonList(roleDTO));

        ResponseEntity<ApiResponse<List<RoleDTO>>> response = roleController.getAllRoles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody());
        assertEquals(roleDTO, response.getBody());
    }

    @Test
    void testGetRoleById() {
        RoleDTO roleDTO = new RoleDTO();

        when(roleService.getRoleById(1L)).thenReturn(roleDTO);

        ResponseEntity<ApiResponse<RoleDTO>> response = roleController.getRoleById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roleDTO, response.getBody());
    }

    @Test
    void testCreateRole() {
        RoleDTO roleDTO = new RoleDTO();
        RoleDTO createdRoleDTO = new RoleDTO();

        when(roleService.createRole(roleDTO)).thenReturn(createdRoleDTO);

        ResponseEntity<ApiResponse<RoleDTO>> response = roleController.createRole(roleDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRoleDTO, response.getBody());
    }

    @Test
    void testUpdateRole() {
        RoleDTO roleDTO = new RoleDTO();
        RoleDTO updatedRoleDTO = new RoleDTO();

        when(roleService.updateRole(roleDTO)).thenReturn(updatedRoleDTO);

        ResponseEntity<ApiResponse<RoleDTO>> response = roleController.updateRole(roleDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRoleDTO, response.getBody());
    }

    @Test
    void testDeleteRole() {
        doNothing().when(roleService).deleteRole(1L);

        ResponseEntity<ApiResponse<Void>> response = roleController.deleteRole(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

