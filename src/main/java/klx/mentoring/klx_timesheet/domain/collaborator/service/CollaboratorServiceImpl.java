package klx.mentoring.klx_timesheet.domain.collaborator.service;

import klx.mentoring.klx_timesheet.domain.collaborator.exceptions.InvalidCollaboratorDataException;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence.CollaboratorRepositoryPort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CollaboratorServiceImpl implements CollaboratorServicePort {

    private final CollaboratorRepositoryPort repository;

    public CollaboratorServiceImpl(CollaboratorRepositoryPort collaboratorRepository) {
        this.repository = collaboratorRepository;
    }

    @Override
    public List<Collaborator> findAll() {
        List<Collaborator> collaborators = this.repository.findAll();
        return collaborators;
    }

    @Override
    public Optional<Collaborator> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Override
    public Collaborator create(Collaborator collaborator)  {
        Map<String, String> errors = collaborator.validate();
        if (!errors.isEmpty()) {
            throw new InvalidCollaboratorDataException(
                "The collaborator cannot be created because its data has errors:", errors);
        }
        return this.repository.create(collaborator);
    }

    @Override
    public Optional<Collaborator> update(UUID id, Collaborator collaborator) {
        Map<String, String> errors = collaborator.validate();
        if (!errors.isEmpty()) {
            throw new InvalidCollaboratorDataException(
                "The collaborator cannot be updated because its data has errors:", errors);
        }
        return repository.update(collaborator, id);
    }

    @Override
    public Optional<Collaborator> deleteById(UUID id) {
        Optional<Collaborator> optionalCollaborator = this.repository.findById(id);
        if(optionalCollaborator.isPresent()){
            repository.deleteById(id);
            return optionalCollaborator;
        }
        return Optional.empty();
    }
    
}
