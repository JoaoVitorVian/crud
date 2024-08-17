package com.challenge.crud.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.crud.dto.Person.PersonCreateDTO;
import com.challenge.crud.dto.Person.PersonDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.model.Person;
import com.challenge.crud.repository.EstablishmentRepository;
import com.challenge.crud.repository.PersonRepository;
import com.challenge.crud.repository.RoleRepository;
import com.challenge.crud.util.StringExtensions;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<PersonDTO> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));

        return modelMapper.map(person, PersonDTO.class);
    }

    @Transactional
    public PersonCreateDTO createPerson(PersonCreateDTO personDTO) {
        validatePerson(personDTO);
        validateCpf(personDTO.getCpf());

        if(personRepository.existsByEmail(personDTO.getEmail())){
            throw new ResourceNotFoundException("EMAIL already exists: " + personDTO.getEmail());
        }

        Person person = modelMapper.map(personDTO, Person.class);

        String cpfWithoutMask = StringExtensions.removeNonDigits(personDTO.getCpf());
        person.setCpf(cpfWithoutMask);

        Person savedPerson = personRepository.save(person);

        return modelMapper.map(savedPerson, PersonCreateDTO.class);
    }

    @Transactional
    public PersonDTO updatePerson(PersonDTO personDTO) {
        validatePerson(personDTO);
    
        Long id = personDTO.getId();
        if (id == null) {
            throw new ResourceNotFoundException("ID cannot be null for update");
        }
    
        String cpfWithoutMask = StringExtensions.removeNonDigits(personDTO.getCpf());
        if (personRepository.existsByCpfAndIdNot(cpfWithoutMask, id)) {
            throw new ResourceNotFoundException("CPF " + personDTO.getCpf() + " is already in use by another person.");
        }
    
        if (personRepository.existsByEmailAndIdNot(personDTO.getEmail(), id)) {
            throw new ResourceNotFoundException("Email " + personDTO.getEmail() + " is already in use by another person.");
        }
    
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
    
        modelMapper.map(personDTO, existingPerson);
    
        existingPerson.setCpf(cpfWithoutMask);
        existingPerson.setEstablishment(personDTO.getEstablishment() != null ?
                establishmentRepository.findById(personDTO.getEstablishment().getId()).orElse(null) : null);
        existingPerson.setRole(personDTO.getRole() != null ?
                roleRepository.findById(personDTO.getRole().getId()).orElse(null) : null);
    
        Person updatedPerson = personRepository.save(existingPerson);
        return modelMapper.map(updatedPerson, PersonDTO.class);
    }

    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person not found with id " + id);
        }

        personRepository.deleteById(id);
    }

    private void validatePerson(PersonDTO personDTO) {
        String cpfWithoutMask = StringExtensions.removeNonDigits(personDTO.getCpf());
        if (cpfWithoutMask == null || cpfWithoutMask.trim().isEmpty()) {
            throw new ResourceNotFoundException("Invalid CPF cannot be null or empty: " + personDTO.getCpf());
        }

        if (personDTO.getBirthDate() == null) {
            throw new ResourceNotFoundException("Birth date cannot be null !");
        }

        if (personDTO.getName() == null || personDTO.getName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Name cannot be null or empty !");
        }

        if (personDTO.getAdmissionDate() == null) {
            throw new ResourceNotFoundException("Admission Date cannot be null !");
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = personDTO.getBirthDate();
        int age = Period.between(birthDate, today).getYears();
        if (age < 18) {
            throw new ResourceNotFoundException("Person must be at least 18 years old. Current age: " + age);
        }
    }

    private void validatePerson(PersonCreateDTO personDTO) {
        String cpfWithoutMask = StringExtensions.removeNonDigits(personDTO.getCpf());
        if (cpfWithoutMask == null || cpfWithoutMask.trim().isEmpty()) {
            throw new ResourceNotFoundException("Invalid CPF cannot be null or empty: " + personDTO.getCpf());
        }

        if (personDTO.getBirthDate() == null) {
            throw new ResourceNotFoundException("Birth date cannot be null !");
        }

        if (personDTO.getName() == null || personDTO.getName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Name cannot be null or empty !");
        }

        if (personDTO.getAdmissionDate() == null) {
            throw new ResourceNotFoundException("Admission Date cannot be null !");
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = personDTO.getBirthDate();
        int age = Period.between(birthDate, today).getYears();
        if (age < 18) {
            throw new ResourceNotFoundException("Person must be at least 18 years old. Current age: " + age);
        }
    }

    private void validateCpf(String cpf) {
        String cpfWithoutMask = StringExtensions.removeNonDigits(cpf);

        if (personRepository.existsByCpf(cpfWithoutMask)) {
            throw new ResourceNotFoundException("CPF already exists: " + cpf);
        }
    }
}

