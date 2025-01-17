package klx.mentoring.klx_timesheet.infrastructure.businessunit.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.persistence.BusinessUnitRepositoryPort;
import klx.mentoring.klx_timesheet.infrastructure.businessunit.model.BusinessUnityEntity;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.persistence.JpaCollaboratorRepository;

import static klx.mentoring.klx_timesheet.infrastructure.mappers.BusinessUnitMapper.*;

@Repository
public class BusinessRepository implements BusinessUnitRepositoryPort {

    @Autowired
    JpaBusinessUnityRepository businessUnityRepository;

    @Autowired
    JpaCollaboratorRepository collaboratorRepository;

    @Override
    public List<BusinessUnit> findAll() {
        List<BusinessUnityEntity> businessUnitEntities = this.businessUnityRepository.findAll();
        return businessUnitEntities.stream().map(bUE -> businessUnityEntityToRecord(bUE)).collect(Collectors.toList());
    }

    @Override
    public Optional<BusinessUnit> findById(UUID id) {
        return businessUnityRepository.findById(id).map(bUE -> businessUnityEntityToRecord(bUE));
    }

    @Override
    public BusinessUnit create(BusinessUnit businessUnit) throws NotFoundCollaboratorException {
        BusinessUnityEntity businessUnityEntity = new BusinessUnityEntity();
        List<UUID> collaboratorIds = businessUnit.collaborators().stream().
            map(c -> c.id()).collect(Collectors.toList());
        Set<CollaboratorEntity> collaboratorEntities = getCollaboratorEntities(collaboratorIds);
        businessUnityEntity.setCollaborators(collaboratorEntities);
        businessUnityEntity.setName(businessUnit.name());
        return businessUnityEntityToRecord(businessUnityRepository.save(businessUnityEntity));
    }

    @Override
    public Optional<BusinessUnit> update(BusinessUnit businessUnit, UUID id) throws NotFoundCollaboratorException {
        List<UUID> collaboratorIds = businessUnit.collaborators().stream().
            map(c -> c.id()).collect(Collectors.toList());
        Set<CollaboratorEntity> collaboratorEntities = getCollaboratorEntities(collaboratorIds);

        return businessUnityRepository.findById(id)
                .map(businessUnitEntity -> {
                    businessUnitEntity.setName(businessUnit.name());
                    businessUnitEntity.setCollaborators(collaboratorEntities);
                    BusinessUnityEntity savedEntity = businessUnityRepository.save(businessUnitEntity);
                    return businessUnityEntityToRecord(savedEntity);
                });
    }

    @Override
    public void deleteById(UUID id) {
        this.businessUnityRepository.deleteById(id);
    }

    @Override
    public Optional<BusinessUnit> addCollaborators(List<UUID> collaboratorIds, UUID id) {
        return this.businessUnityRepository.findById(id)
                .map(businessUnitEntity -> {
                    Set<CollaboratorEntity> collaboratorEntities = getCollaboratorEntities(collaboratorIds);
                    businessUnitEntity.addCollaborators(collaboratorEntities);
                    return businessUnityEntityToRecord(this.businessUnityRepository.save(businessUnitEntity));
                });
    }

    @Override
    public Optional<BusinessUnit> removeCollaborators(List<UUID> collaboratorIds, UUID id) {
        return this.businessUnityRepository.findById(id)
                .map(businessUnitEntity -> {
                    Set<CollaboratorEntity> collaboratorEntities = getCollaboratorEntities(collaboratorIds);
                    businessUnitEntity.removeCollaborators(collaboratorEntities);
                    return businessUnityEntityToRecord(this.businessUnityRepository.save(businessUnitEntity));
                });
    }

    private Set<CollaboratorEntity> getCollaboratorEntities(List<UUID> collaboratorIds) {
        Set<CollaboratorEntity> collaboratorEntities = this.collaboratorRepository
                .findByIdIn(collaboratorIds).stream().collect(Collectors.toSet());
        return collaboratorEntities;
    }

}
