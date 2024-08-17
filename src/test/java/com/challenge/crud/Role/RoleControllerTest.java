package com.challenge.crud.Role;

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
import com.challenge.crud.controller.RoleController;
import com.challenge.crud.dto.Role.RoleCreateDTO;
import com.challenge.crud.dto.Role.RoleDTO;
import com.challenge.crud.service.RoleService;

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
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(roleDTO, response.getBody().getData().get(0));
    }

    @Test
    void testGetRoleById() {
        RoleDTO roleDTO = new RoleDTO();

        when(roleService.getRoleById(1L)).thenReturn(roleDTO);

        ResponseEntity<ApiResponse<RoleDTO>> response = roleController.getRoleById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(roleDTO, response.getBody().getData());
    }

    @Test
    void testCreateRole() {
        RoleCreateDTO roleDTO = new RoleCreateDTO();
        RoleCreateDTO createdRoleDTO = new RoleCreateDTO();

        when(roleService.createRole(roleDTO)).thenReturn(createdRoleDTO);

        ResponseEntity<ApiResponse<RoleCreateDTO>> response = roleController.createRole(roleDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdRoleDTO, response.getBody().getData());
    }

    @Test
    void testUpdateRole() {
        RoleDTO roleDTO = new RoleDTO();
        RoleDTO updatedRoleDTO = new RoleDTO();

        when(roleService.updateRole(roleDTO)).thenReturn(updatedRoleDTO);

        ResponseEntity<ApiResponse<RoleDTO>> response = roleController.updateRole(roleDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedRoleDTO, response.getBody().getData());
    }

    @Test
    void testDeleteRole() {
        doNothing().when(roleService).deleteRole(1L);

        ResponseEntity<ApiResponse<Void>> response = roleController.deleteRole(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody()); 
    }
}
