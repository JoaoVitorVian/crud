package com.challenge.crud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.crud.dto.Establishment.EstablishmentCreateDTO;
import com.challenge.crud.dto.Establishment.EstablishmentDTO;
import com.challenge.crud.exceptions.ResourceNotFoundException;
import com.challenge.crud.model.Establishment;
import com.challenge.crud.repository.EstablishmentRepository;
import com.challenge.crud.repository.PersonRepository;

@Service
public class EstablishmentService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    ModelMapper modelMapper;


    public EstablishmentDTO getEstablishmentById(Long id) {
        Establishment establishment = establishmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id " + id));

        return modelMapper.map(establishment, EstablishmentDTO.class);
    }

    public List<EstablishmentDTO> getAllEstablishments() {
        return establishmentRepository.findAll().stream()
                .map(establishment -> modelMapper.map(establishment, EstablishmentDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public EstablishmentCreateDTO createEstablishment(EstablishmentCreateDTO establishmentDTO) {
        validateEstablishmentDTO(establishmentDTO);

        Establishment establishment = modelMapper.map(establishmentDTO, Establishment.class);
        Establishment createdEstablishment = establishmentRepository.save(establishment);

        return modelMapper.map(createdEstablishment, EstablishmentCreateDTO.class);
    }

    @Transactional
    public EstablishmentDTO updateEstablishment(EstablishmentDTO establishmentDTO) {
        validateEstablishmentDTO(establishmentDTO);

        Long id = establishmentDTO.getId();
        if (id == null) {
            throw new ResourceNotFoundException("ID cannot be null for update");
        }

        Establishment existingEstablishment = establishmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id " + id));

        modelMapper.map(establishmentDTO, existingEstablishment);
        Establishment updatedEstablishment = establishmentRepository.save(existingEstablishment);

        return modelMapper.map(updatedEstablishment, EstablishmentDTO.class);
    }

    @Transactional
    public void deleteEstablishment(Long id) {
        Establishment establishment = establishmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id " + id));
    
        establishmentRepository.findPersonsByEstablishmentId(id).forEach(person -> {
            person.setEstablishment(null);

            personRepository.save(person);
        });
    
        establishmentRepository.delete(establishment);
    }

    private void validateEstablishmentDTO(EstablishmentDTO establishmentDTO) {
        if (establishmentDTO.getName() == null || establishmentDTO.getName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Name cannot be null or empty !");
        }

        if (establishmentRepository.existsByName(establishmentDTO.getName())) {
            throw new ResourceNotFoundException("Establishment with name " + establishmentDTO.getName() + " already exists");
        }
    }

    private void validateEstablishmentDTO(EstablishmentCreateDTO establishmentDTO) {
        if (establishmentDTO.getName() == null || establishmentDTO.getName().trim().isEmpty()) {
            throw new ResourceNotFoundException("Name cannot be null or empty !");
        }

        if (establishmentRepository.existsByName(establishmentDTO.getName())) {
            throw new ResourceNotFoundException("Establishment with name " + establishmentDTO.getName() + " already exists");
        }
    }
}
