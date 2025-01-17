package klx.mentoring.klx_timesheet.domain.businessunit.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import java.util.stream.Collectors;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.persistence.BusinessUnitRepositoryPort;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.service.BusinessUnitServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence.CollaboratorRepositoryPort;

public class BusinessUnitServiceImpl implements BusinessUnitServicePort {

    private final BusinessUnitRepositoryPort businessRepository;
    private final CollaboratorRepositoryPort collaboratorRepository;

    public BusinessUnitServiceImpl( BusinessUnitRepositoryPort businessUnitRepository,
                                    CollaboratorRepositoryPort collaboratorRepository) {
            this.businessRepository = businessUnitRepository;
            this.collaboratorRepository = collaboratorRepository;
    }

    @Override
    public List<BusinessUnit> findAll() {
        return this.businessRepository.findAll();
    }

    @Override
    public Optional<BusinessUnit> findById(UUID id) {
        return this.businessRepository.findById(id);
    }

    @Override
    public BusinessUnit create(BusinessUnit businessUnit) throws NotFoundCollaboratorException{
        getValidIdCollaborators(businessUnit.collaborators());
        return this.businessRepository.create(businessUnit);
    }

    @Override
    public Optional<BusinessUnit> update(BusinessUnit businessUnit, UUID id) throws NotFoundCollaboratorException{
        getValidIdCollaborators(businessUnit.collaborators());
        return this.businessRepository.update(businessUnit, id);
    }

    @Override
    public Optional<BusinessUnit> deleteById(UUID id) {
        Optional<BusinessUnit> buOptional = this.businessRepository.findById(id);
        if(buOptional.isPresent()){
            this.businessRepository.deleteById(id);
            return buOptional;
        }
        return Optional.empty();   
    }

    @Override
    public Optional<BusinessUnit> addCollaborators(Set<Collaborator> collaborators, UUID id) 
                                                        throws NotFoundCollaboratorException{
        if(collaborators.isEmpty()){
            throw new NotFoundCollaboratorException("Collaborator set is empty");
        }
        return this.businessRepository.addCollaborators(new ArrayList<UUID>(getValidIdCollaborators(collaborators)), id);
    }

    @Override
    public Optional<BusinessUnit> removeCollaborators(Set<Collaborator> collaborators, UUID id)
                                                        throws NotFoundCollaboratorException {
        if(collaborators.isEmpty()){
            throw new NotFoundCollaboratorException("Collaborator set is empty");
        }
        return this.businessRepository.removeCollaborators(new ArrayList<UUID>(getValidIdCollaborators(collaborators)), id);
    }

    private Set<UUID> getValidIdCollaborators(Set<Collaborator> collaborators)
                                                        throws NotFoundCollaboratorException {
        
        Set<UUID> validCollaborators = getIdCollaborators(collaborators);
        Set<UUID> receivedCollaborators = collaborators.stream().
            map(c -> c.id()).collect(Collectors.toSet());
        boolean result = receivedCollaborators.equals(validCollaborators);
        if(!result){
            StringBuilder missingCollaborators = new StringBuilder();
            receivedCollaborators.removeAll(validCollaborators);
            receivedCollaborators.forEach(c -> missingCollaborators.append(c).append(", "));
            throw new NotFoundCollaboratorException("Collaborators not found in the database: " + missingCollaborators);
        }
        return validCollaborators;
    }

    private Set<UUID> getIdCollaborators(Set<Collaborator> collaborators) {
    
        List<UUID> idCollaboratorList = collaborators.stream().map(c -> c.id()).collect(Collectors.toList());
        if(idCollaboratorList.isEmpty()){
            return new HashSet<UUID>();
        }
        return this.collaboratorRepository.findByIdIn(idCollaboratorList).stream()
                .map(c -> c.id()).collect(Collectors.toSet());
    }

}
