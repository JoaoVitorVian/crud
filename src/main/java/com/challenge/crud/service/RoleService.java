package com.challenge.crud.service;

import com.challenge.crud.dto.RoleDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.model.Role;
import com.challenge.crud.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        return modelMapper.map(role, RoleDTO.class);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        validateRoleDTO(roleDTO);

        Role role = modelMapper.map(roleDTO, Role.class);
        Role createdRole = roleRepository.save(role);

        return modelMapper.map(createdRole, RoleDTO.class);
    }

    @Transactional
    public RoleDTO updateRole(RoleDTO roleDTO) {
        validateRoleDTO(roleDTO);

        Long id = roleDTO.getId();
        if (id == null) {
            throw new ResourceNotFoundException("ID cannot be null for update");
        }

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        modelMapper.map(roleDTO, existingRole);
        Role updatedRole = roleRepository.save(existingRole);

        return modelMapper.map(updatedRole, RoleDTO.class);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        roleRepository.delete(role);
    }

    private void validateRoleDTO(RoleDTO roleDTO) {
        if (roleDTO.getName() == null || roleDTO.getName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Name cannot be null or empty !");
        }

        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new ResourceNotFoundException("Role with name " + roleDTO.getName() + " already exists");
        }
    }
}
