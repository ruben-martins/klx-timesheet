package klx.mentoring.klx_timesheet.infrastructure.repositories;

import klx.mentoring.klx_timesheet.domain.exceptions.CollaboratorNotFoundException;
import klx.mentoring.klx_timesheet.domain.ports.repositories.CollaboratorRepositoryPort;
import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;
import klx.mentoring.klx_timesheet.infrastructure.models.CollaboratorEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollaboratorRepository implements CollaboratorRepositoryPort{

    @Autowired
    SpringCollaboratorRepository springCollaboratorRepository;

    @Override
    public List<CollaboratorRecord> findAll() {
        List<CollaboratorEntity> collaboratorsEntities = this.springCollaboratorRepository.findAll();
        return collaboratorsEntities.stream().map(collaborator -> this.toRecord(collaborator)).collect(Collectors.toList());
    }

    @Override
    public CollaboratorRecord findById(UUID id) {
        Optional<CollaboratorEntity> collaboratorEntity = this.springCollaboratorRepository.findById(id);  
        return this.toRecord(collaboratorEntity.get());
    }

    @Override
    public CollaboratorRecord create(CollaboratorRecord collaborator) {
        Objects.requireNonNull(collaborator, "Collaborator record cannot be null");
        
        CollaboratorEntity collaboratorEntity = this.toEntity(collaborator);
    
        collaboratorEntity = this.springCollaboratorRepository.save(collaboratorEntity);
    
        return this.toRecord(collaboratorEntity);
    }
    


    @Override
    public CollaboratorRecord update(UUID id, CollaboratorRecord collaborator) {
        CollaboratorEntity collaboratorEntity = this.springCollaboratorRepository.findById(id)
            .orElseThrow(() -> new CollaboratorNotFoundException("Collaborator with id " + id + " not found"));

        collaboratorEntity.setName(collaborator.name());
        collaboratorEntity.setLastName(collaborator.lastName());
        collaboratorEntity.setEmail(collaborator.email());
        collaboratorEntity.setHireDate(collaborator.hireDate());
        collaboratorEntity.setPosition(collaborator.position());

        CollaboratorEntity updatedEntity = this.springCollaboratorRepository.save(collaboratorEntity);
        
        return this.toRecord(updatedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        CollaboratorEntity collaboratorEntity = springCollaboratorRepository.findById(id)
            .orElseThrow(() -> new CollaboratorNotFoundException("Collaborator with id " + id + " not found"));
    
        springCollaboratorRepository.delete(collaboratorEntity);
    }

    // Método de utilidad para convertir CollaboratorRecord a CollaboratorEntity
    private CollaboratorEntity toEntity(CollaboratorRecord collaborator) {
        CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
        collaboratorEntity.setName(collaborator.name());
        collaboratorEntity.setLastName(collaborator.lastName());
        collaboratorEntity.setEmail(collaborator.email());
        collaboratorEntity.setHireDate(collaborator.hireDate());
        collaboratorEntity.setPosition(collaborator.position());
        return collaboratorEntity;
    }
        
    // Método de conversión de Collaborator a CollaboratorRecord
    private CollaboratorRecord toRecord(CollaboratorEntity collaborator) {
        return new CollaboratorRecord(
                collaborator.getId(),
                collaborator.getName(),
                collaborator.getLastName(),
                collaborator.getEmail(),
                collaborator.getHireDate(),
                collaborator.getPosition()
        );
    }

}
