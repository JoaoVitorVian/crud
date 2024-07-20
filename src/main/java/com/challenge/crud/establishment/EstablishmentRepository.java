package com.challenge.crud.establishment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
    boolean existsByName(String name);
}
