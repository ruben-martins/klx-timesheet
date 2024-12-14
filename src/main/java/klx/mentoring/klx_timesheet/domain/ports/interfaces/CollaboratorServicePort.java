package klx.mentoring.klx_timesheet.domain.ports.interfaces;

import java.util.List;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;

public interface CollaboratorServicePort {

    List<CollaboratorRecord> findAll();

    CollaboratorRecord findById(UUID id);

    CollaboratorRecord create(CollaboratorRecord collaboratorRecord);

    CollaboratorRecord update(UUID id, CollaboratorRecord collaborator);

    void deleteById(UUID id);
}
