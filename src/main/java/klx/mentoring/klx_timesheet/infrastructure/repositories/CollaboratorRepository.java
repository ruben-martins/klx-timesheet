package klx.mentoring.klx_timesheet.infrastructure.repositories;

import klx.mentoring.klx_timesheet.domain.dto.CollaboratorDto;
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
        if(collaboratorEntity.isPresent()){
            CollaboratorEntity collaborator = collaboratorEntity.get();
            return this.toRecord(collaborator);
        }
        throw new RuntimeException("Collaborator is not prsent!");
    }

    @Override
    public CollaboratorRecord create(CollaboratorDto collaborator) {

        if(!Objects.isNull(collaborator)){
            CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
            collaboratorEntity.setName(collaborator.getName());
            collaboratorEntity.setLastName(collaborator.getLastName());
            collaboratorEntity.setEmail(collaborator.getEmail());
            collaboratorEntity.setHireDate(collaborator.getHireDate());
            collaborator.setPosition(collaborator.getPosition());
            collaboratorEntity = this.springCollaboratorRepository.save(collaboratorEntity);
            return this.toRecord(collaboratorEntity);
        }
        throw new NullPointerException();
    }

    @Override
    public CollaboratorRecord update(UUID id, CollaboratorDto collaborator) {
        Optional<CollaboratorEntity> collaboratorOptional = this.springCollaboratorRepository.findById(id);
        if(collaboratorOptional.isPresent()){
            CollaboratorEntity collaboratorEntity = collaboratorOptional.get();
            collaboratorEntity.setName(collaborator.getName());
            collaboratorEntity.setLastName(collaborator.getLastName());
            collaboratorEntity.setEmail(collaborator.getEmail());
            collaboratorEntity.setHireDate(collaborator.getHireDate());
            collaboratorEntity.setPosition(collaborator.getPosition());
            collaboratorEntity = this.springCollaboratorRepository.save(collaboratorEntity);
            return this.toRecord(collaboratorEntity);
        }
        throw new NullPointerException();
    }

    @Override
    public void deleteById(UUID id) {
        this.springCollaboratorRepository.deleteById(id);
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
