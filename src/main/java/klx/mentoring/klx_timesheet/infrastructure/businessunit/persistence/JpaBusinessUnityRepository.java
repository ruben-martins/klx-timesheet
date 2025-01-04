package klx.mentoring.klx_timesheet.infrastructure.businessunit.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import klx.mentoring.klx_timesheet.infrastructure.businessunit.model.BusinessUnityEntity;

public interface JpaBusinessUnityRepository extends JpaRepository<BusinessUnityEntity, UUID>{

    @Query("SELECT bu from BusinessUnityEntity bu LEFT JOIN FETCH bu.collaborators where bu.id=?1")
    Optional<BusinessUnityEntity> findOneWithCollaborators(UUID id);
}
