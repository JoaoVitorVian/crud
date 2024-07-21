package com.challenge.crud.person;

import com.challenge.crud.establishment.Establishment;
import com.challenge.crud.establishment.EstablishmentRepository;
import com.challenge.crud.role.Role;
import com.challenge.crud.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.challenge.crud.core.Exceptions.ResourceNotFoundException;
import com.challenge.crud.core.Utils.StringExtensions;

import java.time.Period;
import java.util.List;
import java.time.LocalDate;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final EstablishmentRepository establishmentRepository;
    private final RoleRepository roleRepository;
    private final StringExtensions stringExtensions;

    @Autowired
    public PersonService(PersonRepository personRepository, EstablishmentRepository establishmentRepository,
                         RoleRepository roleRepository, StringExtensions stringExtensions) {
        this.personRepository = personRepository;
        this.establishmentRepository = establishmentRepository;
        this.roleRepository = roleRepository;
        this.stringExtensions = stringExtensions;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
    }

    @Transactional
    public Person createPerson(Person person) {
        Validate(person);
        validateCpf(person.getCpf());

        String cpfWithoutMask = stringExtensions.removeNonDigits(person.getCpf());
        person.setCpf(cpfWithoutMask);

        return personRepository.save(person);
    }

    @Transactional
    public Person updatePerson(Person personDetails) {
        Validate(personDetails);

        Long id = personDetails.getId();
        if (id == null) {
            throw new ResourceNotFoundException("ID cannot be null for update");
        }

        String cpfWithoutMask = stringExtensions.removeNonDigits(personDetails.getCpf());

        Person person = getPersonById(id);
        person.setName(personDetails.getName());
        person.setCpf(cpfWithoutMask);
        person.setBirthDate(personDetails.getBirthDate());
        person.setAdmissionDate(personDetails.getAdmissionDate());
        person.setEmail(personDetails.getEmail());
        person.setEstablishment(personDetails.getEstablishment());
        person.setRole(personDetails.getRole());

        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person not found with id " + id);
        }

        personRepository.deleteById(id);
    }

    private void validateCpf(String cpf) {
        if (cpf == null) {
            throw new ResourceNotFoundException("Invalid CPF: " + cpf);
        }

        if (personRepository.existsByCpf(cpf)) {
            throw new ResourceNotFoundException("CPF already exists: " + cpf);
        }
    }

    private void Validate(Person person){
        if (person.getEstablishment() != null) {
            Establishment establishment = establishmentRepository
                    .findById(person.getEstablishment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id " + person.getEstablishment().getId()));
            person.setEstablishment(establishment);
        }

        if (person.getRole() != null) {
            Role role = roleRepository
                    .findById(person.getRole().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + person.getRole().getId()));
            person.setRole(role);
        }

        if (person.getBirthDate() == null) {
            throw new ResourceNotFoundException("Birth date cannot be null !");
        }

        if(person.getEmail() == null) {
            throw new ResourceNotFoundException("Email cannot be null !");
        }

        if(person.getName() == null) {
            throw new ResourceNotFoundException("Name cannot be null !");
        }

        if(person.getAdmissionDate() == null) {
            throw new ResourceNotFoundException("Admission Date cannot be null !");
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = person.getBirthDate();
        int age = Period.between(birthDate, today).getYears();
        if (age < 18) {
            throw new ResourceNotFoundException("Person must be at least 18 years old. Current age: " + age);
        }
    }
}
