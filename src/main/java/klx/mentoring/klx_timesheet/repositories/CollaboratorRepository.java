package klx.mentoring.klx_timesheet.repositories;

import klx.mentoring.klx_timesheet.models.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}
