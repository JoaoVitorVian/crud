package com.challenge.crud.domain.repository;

import com.challenge.crud.domain.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
    boolean existsByName(String name);
}
