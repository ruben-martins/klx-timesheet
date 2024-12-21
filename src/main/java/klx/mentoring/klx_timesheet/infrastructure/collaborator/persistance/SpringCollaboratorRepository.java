package klx.mentoring.klx_timesheet.infrastructure.collaborator.persistance;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

@Repository
public interface SpringCollaboratorRepository extends JpaRepository<CollaboratorEntity, UUID> {
}
