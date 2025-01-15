package klx.mentoring.klx_timesheet.infrastructure.utils;

import java.util.Set;
import java.util.stream.Collectors;

import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.businessunit.model.BusinessUnityEntity;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

public final class Util {

    public static Collaborator colloboratorEntitytoRecord(CollaboratorEntity collaborator) {
        return new Collaborator(
                collaborator.getId(),
                collaborator.getName(),
                collaborator.getLastName(),
                collaborator.getEmail(),
                collaborator.getHireDate(),
                collaborator.getPosition());
    }

    public static CollaboratorEntity newCollaboratorRecordtoEntity(Collaborator collaborator) {
        CollaboratorEntity collaboratorEntity = new CollaboratorEntity();
        collaboratorEntity.setName(collaborator.name());
        collaboratorEntity.setLastName(collaborator.lastName());
        collaboratorEntity.setEmail(collaborator.email());
        collaboratorEntity.setHireDate(collaborator.hireDate());
        collaboratorEntity.setPosition(collaborator.position());
        return collaboratorEntity;
    }

    public static CollaboratorEntity collaboratorRecordtoEntity(Collaborator collaborator) {
        CollaboratorEntity collaboratorEntity = 
            new CollaboratorEntity( collaborator.id(),
                                    collaborator.name(),
                                    collaborator.lastName(),
                                    collaborator.email(),
                                    collaborator.hireDate(),
                                    collaborator.position());
        return collaboratorEntity;
    }

    public static BusinessUnit businessUnityEntityToRecord(BusinessUnityEntity businessUnityEntity) {
        Set<Collaborator> collaborators = businessUnityEntity.getCollaborators().stream()
                .map(bUE -> colloboratorEntitytoRecord(bUE)).collect(Collectors.toSet());

        return new BusinessUnit(businessUnityEntity.getId(),
                businessUnityEntity.getName(),
                collaborators);
    }

}
