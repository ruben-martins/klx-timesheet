package klx.mentoring.klx_timesheet.infrastructure.collaborator.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

@Repository
public interface JpaCollaboratorRepository extends JpaRepository<CollaboratorEntity, UUID> {

    List<CollaboratorEntity> findByIdIn(List<UUID> ids);
}
