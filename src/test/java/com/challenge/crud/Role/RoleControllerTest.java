package com.challenge.crud.Role;

import com.challenge.crud.role.RoleService;
import com.challenge.crud.role.RoleMapper;
import com.challenge.crud.role.RoleController;
import com.challenge.crud.role.Role;
import com.challenge.crud.role.RoleDTO;
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

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleController roleController;

    public RoleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        Role role = new Role(); // Setup role instance
        RoleDTO roleDTO = new RoleDTO(); // Setup roleDTO instance

        when(roleService.getAllRoles()).thenReturn(Collections.singletonList(role));
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        ResponseEntity<List<RoleDTO>> response = roleController.getAllRoles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(roleDTO, response.getBody().get(0));
    }

    @Test
    void testGetRoleById() {
        Role role = new Role(); // Setup role instance
        RoleDTO roleDTO = new RoleDTO(); // Setup roleDTO instance

        when(roleService.getRoleById(1L)).thenReturn(role);
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        ResponseEntity<RoleDTO> response = roleController.getRoleById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roleDTO, response.getBody());
    }

    @Test
    void testCreateRole() {
        RoleDTO roleDTO = new RoleDTO(); // Setup roleDTO instance
        Role role = new Role(); // Setup role instance
        Role createdRole = new Role(); // Setup created role instance
        RoleDTO createdRoleDTO = new RoleDTO(); // Setup createdRoleDTO instance

        when(roleMapper.toEntity(roleDTO)).thenReturn(role);
        when(roleService.createRole(role)).thenReturn(createdRole);
        when(roleMapper.toDTO(createdRole)).thenReturn(createdRoleDTO);

        ResponseEntity<RoleDTO> response = roleController.createRole(roleDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRoleDTO, response.getBody());
    }

    @Test
    void testUpdateRole() {
        RoleDTO roleDTO = new RoleDTO(); // Setup roleDTO instance
        Role role = new Role(); // Setup role instance
        Role updatedRole = new Role(); // Setup updated role instance
        RoleDTO updatedRoleDTO = new RoleDTO(); // Setup updatedRoleDTO instance

        when(roleMapper.toEntity(roleDTO)).thenReturn(role);
        when(roleService.updateRole(role)).thenReturn(updatedRole);
        when(roleMapper.toDTO(updatedRole)).thenReturn(updatedRoleDTO);

        ResponseEntity<RoleDTO> response = roleController.updateRole(roleDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRoleDTO, response.getBody());
    }

    @Test
    void testDeleteRole() {
        doNothing().when(roleService).deleteRole(1L);

        ResponseEntity<Void> response = roleController.deleteRole(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

