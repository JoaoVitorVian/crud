package com.challenge.crud.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByCpf(String cpf);
}
