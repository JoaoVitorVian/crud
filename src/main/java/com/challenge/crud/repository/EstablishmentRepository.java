package com.challenge.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.challenge.crud.model.Establishment;
import com.challenge.crud.model.Person;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long>{
    boolean existsByName(String name);
    @Query("SELECT p FROM Person p WHERE p.establishment.id = :establishmentId")
    List<Person> findPersonsByEstablishmentId(@Param("establishmentId") Long establishmentId);
}
