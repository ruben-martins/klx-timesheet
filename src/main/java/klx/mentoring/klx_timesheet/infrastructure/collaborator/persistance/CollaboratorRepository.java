package klx.mentoring.klx_timesheet.infrastructure.collaborator.persistance;

import klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence.CollaboratorRepositoryPort;
import klx.mentoring.klx_timesheet.domain.collaborator.record.CollaboratorRecord;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

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
    SpringCollaboratorRepository repository;

    @Override
    public List<CollaboratorRecord> findAll() {
        List<CollaboratorEntity> collaboratorsEntities = this.repository.findAll();
        return collaboratorsEntities.stream().map(collaborator -> this.toRecord(collaborator)).collect(Collectors.toList());
    }

    @Override
    public Optional<CollaboratorRecord> findById(UUID id) {
        return repository.findById(id) // Devuelve Optional<CollaboratorEntity>
                .map(this::toRecord);  // Mapea CollaboratorEntity a CollaboratorRecord
    }

    @Override
    public CollaboratorRecord create(CollaboratorRecord collaborator) {
        Objects.requireNonNull(collaborator, "Collaborator record cannot be null");
    
        CollaboratorEntity collaboratorEntity = this.toEntity(collaborator);

        return this.toRecord(this.repository.save(collaboratorEntity));
    }
    
    @Override
    public Optional<CollaboratorRecord> update(UUID id, CollaboratorRecord collaborator) {
    // Intentamos encontrar el colaborador por ID
    return repository.findById(id) // Devuelve Optional<CollaboratorEntity>
        .map(collaboratorEntity -> { // Si se encuentra, actualizamos los datos
            collaboratorEntity.setName(collaborator.name());
            collaboratorEntity.setLastName(collaborator.lastName());
            collaboratorEntity.setEmail(collaborator.email());
            collaboratorEntity.setHireDate(collaborator.hireDate());
            collaboratorEntity.setPosition(collaborator.position());
            return this.toRecord(repository.save(collaboratorEntity));
        });
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
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
