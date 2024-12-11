package klx.mentoring.klx_timesheet.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import klx.mentoring.klx_timesheet.infrastructure.models.CollaboratorEntity;

@Repository
public interface SpringCollaboratorRepository extends JpaRepository<CollaboratorEntity, UUID> {
}
