package klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.collaborator.record.CollaboratorRecord;

public interface CollaboratorRepositoryPort {

    List<CollaboratorRecord> findAll();

    Optional<CollaboratorRecord> findById(UUID  id);
    
    CollaboratorRecord create(CollaboratorRecord collaborator);

    Optional<CollaboratorRecord> update(UUID id, CollaboratorRecord collaborator);

    void deleteById(UUID id);
}
