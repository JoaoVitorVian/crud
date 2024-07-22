package com.challenge.crud.establishment;

import com.challenge.crud.core.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    public Establishment getEstablishmentById(Long id) {
        return establishmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id " + id));
    }

    public List<Establishment> getAllEstablishments() {
        return establishmentRepository.findAll();
    }

    public Establishment createEstablishment(Establishment establishment) {
        Validate(establishment);

        return establishmentRepository.save(establishment);
    }

    public Establishment updateEstablishment(Establishment establishmentDetails) {
        Validate(establishmentDetails);

        Long id = establishmentDetails.getId();
        if (id == null) {
            throw new ResourceNotFoundException("ID cannot be null for update");
        }

        Establishment existingEstablishment = getEstablishmentById(id);

        existingEstablishment.setName(establishmentDetails.getName());

        return establishmentRepository.save(existingEstablishment);
    }

    public void deleteEstablishment(Long id) {
        Establishment establishment = getEstablishmentById(id);
        establishmentRepository.delete(establishment);
    }

    private void Validate(Establishment data){
        if (establishmentRepository.existsByName(data.getName())) {
            throw new ResourceNotFoundException("Establishment with name " + data.getName() + " already exists");
        }
    }
}
