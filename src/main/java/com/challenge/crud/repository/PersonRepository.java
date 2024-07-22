package com.challenge.crud.domain.repository;

import com.challenge.crud.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
