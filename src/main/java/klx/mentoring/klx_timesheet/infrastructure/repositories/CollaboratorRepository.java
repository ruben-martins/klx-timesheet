package klx.mentoring.klx_timesheet.infrastructure.repositories;

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
    public Optional<CollaboratorRecord> findById(UUID id) {
        return springCollaboratorRepository.findById(id) // Devuelve Optional<CollaboratorEntity>
                .map(this::toRecord); // Mapea CollaboratorEntity a CollaboratorRecord
    }

    @Override
    public CollaboratorRecord create(CollaboratorRecord collaborator) {
        Objects.requireNonNull(collaborator, "Collaborator record cannot be null");
    
        CollaboratorEntity collaboratorEntity = this.toEntity(collaborator);

        collaboratorEntity = this.springCollaboratorRepository.save(collaboratorEntity);

        return this.toRecord(collaboratorEntity);
    }
    
    @Override
    public Optional<CollaboratorRecord> update(UUID id, CollaboratorRecord collaborator) {
    // Intentamos encontrar el colaborador por ID
    return springCollaboratorRepository.findById(id) // Devuelve Optional<CollaboratorEntity>
        .map(collaboratorEntity -> { // Si se encuentra, actualizamos los datos
            collaboratorEntity.setName(collaborator.name());
            collaboratorEntity.setLastName(collaborator.lastName());
            collaboratorEntity.setEmail(collaborator.email());
            collaboratorEntity.setHireDate(collaborator.hireDate());
            collaboratorEntity.setPosition(collaborator.position());
            return this.toRecord(springCollaboratorRepository.save(collaboratorEntity));
        });
    }

    @Override
    public void deleteById(UUID id) {
        springCollaboratorRepository.deleteById(id);
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
