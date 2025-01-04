package klx.mentoring.klx_timesheet.infrastructure.collaborator.persistence;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence.CollaboratorRepositoryPort;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static klx.mentoring.klx_timesheet.infrastructure.utils.Util.*;

@Repository
public class CollaboratorRepository implements CollaboratorRepositoryPort{

    @Autowired
    JpaCollaboratorRepository repository;

    @Override
    public List<Collaborator> findAll() {
        List<CollaboratorEntity> collaboratorsEntities = this.repository.findAll();
        return collaboratorsEntities.stream().map(collaborator -> colloboratorEntitytoRecord(collaborator)).collect(Collectors.toList());
    }

    @Override
    public Optional<Collaborator> findById(UUID id) {
        return repository.findById(id).map(collaborator -> colloboratorEntitytoRecord(collaborator));
    }

    @Override
    public Collaborator create(Collaborator collaborator) {
        return colloboratorEntitytoRecord(this.repository.save(collaboratorRecordtoEntity(collaborator)));
    }
    
    @Override
    public Optional<Collaborator> update(Collaborator collaborator, UUID id) {
        return repository.findById(id)
            .map(collaboratorEntity -> { 
                collaboratorEntity.setName(collaborator.name());
                collaboratorEntity.setLastName(collaborator.lastName());
                collaboratorEntity.setEmail(collaborator.email());
                collaboratorEntity.setHireDate(collaborator.hireDate());
                collaboratorEntity.setPosition(collaborator.position());
                return colloboratorEntitytoRecord(repository.save(collaboratorEntity));
            });
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
