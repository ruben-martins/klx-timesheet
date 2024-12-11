package klx.mentoring.klx_timesheet.domain.ports.repositories;

import java.util.List;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.dto.CollaboratorDto;
import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;

public interface CollaboratorRepositoryPort {

    List<CollaboratorRecord> findAll();

    CollaboratorRecord findById(UUID  id);
    
    CollaboratorRecord create(CollaboratorDto collaborator);

    CollaboratorRecord update(UUID id, CollaboratorDto collaborator);

    void deleteById(UUID id);
}
