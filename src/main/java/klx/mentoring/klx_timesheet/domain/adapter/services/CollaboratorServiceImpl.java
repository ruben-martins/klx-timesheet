package klx.mentoring.klx_timesheet.domain.adapter.services;

import klx.mentoring.klx_timesheet.domain.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.ports.repositories.CollaboratorRepositoryPort;
import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;

import java.util.List;
import java.util.UUID;

public class CollaboratorServiceImpl implements CollaboratorServicePort {

    private final CollaboratorRepositoryPort collaboratorRepository;

    public CollaboratorServiceImpl(CollaboratorRepositoryPort collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    @Override
    public List<CollaboratorRecord> findAll() {
        List<CollaboratorRecord> collaborators = this.collaboratorRepository.findAll();
        return collaborators;
    }

    @Override
    public CollaboratorRecord findById(UUID id) {
        CollaboratorRecord collaborator = this.collaboratorRepository.findById(id);
        if (collaborator != null)
            return collaborator;
        throw new NullPointerException();
    }

    @Override
    public CollaboratorRecord create(CollaboratorRecord collaborator) {
        CollaboratorRecord savedCollaborator = this.collaboratorRepository.create(collaborator);
        return savedCollaborator;
    }

    @Override
    public CollaboratorRecord update(UUID id, CollaboratorRecord collaborator) {
        CollaboratorRecord collaboratorRecord = collaboratorRepository.findById(id);
        if (collaboratorRecord != null && collaborator != null) {
            return collaboratorRepository.update(id, collaborator);
        } else {
            throw new RuntimeException("Collaborator not found with id: " + id);
        }
    }

    @Override
    public void deleteById(UUID id) {
        collaboratorRepository.deleteById(id);
    }

}
