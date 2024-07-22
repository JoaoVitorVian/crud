package com.challenge.crud.repository;

import com.challenge.crud.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
    boolean existsByName(String name);
}
