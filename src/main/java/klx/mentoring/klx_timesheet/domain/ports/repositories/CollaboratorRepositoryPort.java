package klx.mentoring.klx_timesheet.domain.ports.repositories;

import java.util.List;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.exceptions.CollaboratorNotFoundException;
import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;

public interface CollaboratorRepositoryPort {

    List<CollaboratorRecord> findAll();

    CollaboratorRecord findById(UUID  id);
    
    CollaboratorRecord create(CollaboratorRecord collaborator);

    CollaboratorRecord update(UUID id, CollaboratorRecord collaborator) throws CollaboratorNotFoundException;;

    void deleteById(UUID id) throws CollaboratorNotFoundException;
}
