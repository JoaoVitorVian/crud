package com.challenge.crud.role;

import com.challenge.crud.core.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        Validate(role);
        return roleRepository.save(role);
    }

    public Role updateRole(Role roleDetails) {
        Validate(roleDetails);

        Long id = roleDetails.getId();
        if (id == null) {
            throw new ResourceNotFoundException("ID cannot be null for update");
        }

        Role existingRole = getRoleById(id);

        existingRole.setName(roleDetails.getName());

        return roleRepository.save(existingRole);
    }

    public void deleteRole(Long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }

    private void Validate(Role data){
        if(roleRepository.existsByName(data.getName())){
            throw new ResourceNotFoundException("Role with name " + data.getName() + " already exists");
        }
    }
}
