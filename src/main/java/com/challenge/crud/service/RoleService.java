package com.challenge.crud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.crud.dto.Role.RoleCreateDTO;
import com.challenge.crud.dto.Role.RoleDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.model.Role;
import com.challenge.crud.repository.PersonRepository;
import com.challenge.crud.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;


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
    public RoleCreateDTO createRole(RoleCreateDTO roleDTO) {
        validateRoleDTO(roleDTO);

        Role role = modelMapper.map(roleDTO, Role.class);
        Role createdRole = roleRepository.save(role);

        return modelMapper.map(createdRole, RoleCreateDTO.class);
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
    
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        
        roleRepository.findPersonsByRoleId(id).forEach(person -> {
                person.setRole(null);
                personRepository.save(person);
            });
            
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
    private void validateRoleDTO(RoleCreateDTO roleDTO) {
        if (roleDTO.getName() == null || roleDTO.getName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Name cannot be null or empty !");
        }

        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new ResourceNotFoundException("Role with name " + roleDTO.getName() + " already exists");
        }
    }
}
