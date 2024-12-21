package klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.collaborator.record.CollaboratorRecord;

public interface CollaboratorServicePort {

    List<CollaboratorRecord> findAll();

    Optional<CollaboratorRecord> findById(UUID id);

    CollaboratorRecord create(CollaboratorRecord collaboratorRecord);

    Optional<CollaboratorRecord> update(UUID id, CollaboratorRecord collaborator);

    void deleteById(UUID id);
}
