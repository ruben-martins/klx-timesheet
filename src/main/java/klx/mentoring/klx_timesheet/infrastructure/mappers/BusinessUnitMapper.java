package klx.mentoring.klx_timesheet.infrastructure.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.businessunit.model.BusinessUnityEntity;

import static klx.mentoring.klx_timesheet.infrastructure.mappers.CollaboratorMapper.*;

public class BusinessUnitMapper {

    public static BusinessUnit businessUnityEntityToRecord(BusinessUnityEntity businessUnityEntity) {
        Set<Collaborator> collaborators = businessUnityEntity.getCollaborators().stream()
                .map(bUE -> colloboratorEntitytoRecord(bUE)).collect(Collectors.toSet());

        return new BusinessUnit(businessUnityEntity.getId(),
                businessUnityEntity.getName(),
                collaborators);
    }


}
