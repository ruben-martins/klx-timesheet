package klx.mentoring.klx_timesheet.domain.businessunit.ports.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public interface BusinessUnitServicePort {

    List<BusinessUnit> findAll();

    Optional<BusinessUnit> findById(UUID id);

    BusinessUnit create(BusinessUnit businessUnit) throws NotFoundCollaboratorException;

    Optional<BusinessUnit> update(BusinessUnit businessUnit, UUID id) throws NotFoundCollaboratorException;

    Optional<BusinessUnit> deleteById(UUID id);

    Optional<BusinessUnit> addCollaborators(Set<Collaborator> collaborators, UUID id) 
        throws NotFoundCollaboratorException;

    Optional<BusinessUnit> removeCollaborators(Set<Collaborator> collaborators, UUID id)
        throws NotFoundCollaboratorException;

}
