package klx.mentoring.klx_timesheet.infrastructure.mappers;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

public final class CollaboratorMapper {

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

}
