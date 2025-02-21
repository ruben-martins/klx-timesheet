package klx.mentoring.klx_timesheet.domain.businessunit.ports.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.InvalidBusinessDataException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.collaborator.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public interface BusinessUnitServicePort {

    List<BusinessUnit> findAll();

    Optional<BusinessUnit> findById(UUID id);

    BusinessUnit create(BusinessUnit businessUnit) throws InvalidBusinessDataException;

    Optional<BusinessUnit> update(BusinessUnit businessUnit, UUID id) throws InvalidBusinessDataException;

    Optional<BusinessUnit> deleteById(UUID id);

    Optional<BusinessUnit> addCollaborators(Set<Collaborator> collaborators, UUID id) 
        throws NotFoundCollaboratorException;

    Optional<BusinessUnit> removeCollaborators(Set<Collaborator> collaborators, UUID id)
        throws NotFoundCollaboratorException;

}
