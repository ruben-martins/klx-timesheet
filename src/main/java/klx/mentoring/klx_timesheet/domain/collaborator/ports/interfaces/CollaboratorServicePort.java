package klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public interface CollaboratorServicePort {

    List<Collaborator> findAll();

    Optional<Collaborator> findById(UUID id);

    Collaborator create(Collaborator collaboratorRecord);

    Optional<Collaborator> update(UUID id, Collaborator collaborator);

    void deleteById(UUID id);
}
