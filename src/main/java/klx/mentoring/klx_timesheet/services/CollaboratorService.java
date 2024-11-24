package klx.mentoring.klx_timesheet.services;

import klx.mentoring.klx_timesheet.models.Collaborator;
import klx.mentoring.klx_timesheet.records.CollaboratorRecord;

import java.util.List;
import java.util.Optional;

public interface CollaboratorService {

    List<CollaboratorRecord> findAll();

    Optional<CollaboratorRecord> findById(Long id);

    CollaboratorRecord create(Collaborator collaborator);

    CollaboratorRecord update(Long id, Collaborator collaborator);

    void delete(Long id);
    
}
