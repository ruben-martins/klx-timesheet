package klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public interface CollaboratorRepositoryPort {

    List<Collaborator> findAll();

    Optional<Collaborator> findById(UUID  id);
    
    Collaborator create(Collaborator collaborator);

    Optional<Collaborator> update(Collaborator collaborator, UUID id);

    void deleteById(UUID id);
}
