package klx.mentoring.klx_timesheet.domain.businessunit.adapter.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.persistence.BusinessUnitRepositoryPort;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.service.BusinessUnitServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;


public class BusinessUnitServiceImpl implements BusinessUnitServicePort {

    private final BusinessUnitRepositoryPort repository;

    public BusinessUnitServiceImpl(BusinessUnitRepositoryPort businessUnitRepository) {
        this.repository = businessUnitRepository;
    }

    @Override
    public List<BusinessUnit> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<BusinessUnit> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Override
    public BusinessUnit create(BusinessUnit businessUnit) throws NotFoundCollaboratorException{
        return this.repository.create(businessUnit);
    }

    @Override
    public Optional<BusinessUnit> update(BusinessUnit businessUnit, UUID id) 
                                                            throws NotFoundCollaboratorException{
        return this.repository.update(businessUnit, id);
    }

    @Override
    public Optional<BusinessUnit> deleteById(UUID id) {
        Optional<BusinessUnit> buOptional = this.repository.findById(id);
        if(buOptional.isPresent()){
            this.repository.deleteById(id);
            return buOptional;
        }
        return Optional.empty();   
    }

    @Override
    public Optional<BusinessUnit> addCollaborators(Set<Collaborator> collaborators, UUID id) 
                                                        throws NotFoundCollaboratorException{
        return this.repository.addCollaborators(collaborators, id);
    }

    @Override
    public Optional<BusinessUnit> removeCollaborators(Set<Collaborator> collaborators, UUID id)
                                                        throws NotFoundCollaboratorException {
        return this.repository.removeCollaborators(collaborators, id);
    }

}
