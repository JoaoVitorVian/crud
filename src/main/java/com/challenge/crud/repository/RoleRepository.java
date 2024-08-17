package com.challenge.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.challenge.crud.model.Person;
import com.challenge.crud.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
    @Query("SELECT p FROM Person p WHERE p.role.id = :roleId")
    List<Person> findPersonsByRoleId(@Param("roleId") Long roleId);
}
