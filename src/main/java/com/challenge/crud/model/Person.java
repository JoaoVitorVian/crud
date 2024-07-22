package com.challenge.crud.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CPF(message = "Invalid CPF")
    @Column(unique = true)
    private String cpf;

    private LocalDate birthDate;

    private LocalDate admissionDate;

    private String email;

    @ManyToOne
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
