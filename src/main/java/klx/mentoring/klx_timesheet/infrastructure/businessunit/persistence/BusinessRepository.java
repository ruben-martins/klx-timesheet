package klx.mentoring.klx_timesheet.infrastructure.businessunit.persistence;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.persistence.BusinessUnitRepositoryPort;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.businessunit.model.BusinessUnityEntity;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.persistence.JpaCollaboratorRepository;

import static klx.mentoring.klx_timesheet.infrastructure.utils.Util.*;

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
        Set<CollaboratorEntity> collaboratorEntities = businessUnit.collaborators().stream()
                .map(c -> collaboratorRecordtoEntity(c)).collect(Collectors.toSet());
        businessUnityEntity.setCollaborators(collaboratorEntities);
        businessUnityEntity.setName(businessUnit.name());
        return businessUnityEntityToRecord(businessUnityRepository.save(businessUnityEntity));
    }

    @Override
    public Optional<BusinessUnit> update(BusinessUnit businessUnit, UUID id) throws NotFoundCollaboratorException {
        Set<CollaboratorEntity> validCollaborators = getValidCollaborators(businessUnit.collaborators());

        return businessUnityRepository.findById(id)
                .map(businessUnitEntity -> {
                    businessUnitEntity.setName(businessUnit.name());
                    businessUnitEntity.setCollaborators(validCollaborators);
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
                    Set<CollaboratorEntity> collaboratorEntities = this.collaboratorRepository
                            .findByIdIn(collaboratorIds).stream().collect(Collectors.toSet());
                    businessUnitEntity.addCollaborators(collaboratorEntities);
                    return businessUnityEntityToRecord(this.businessUnityRepository.save(businessUnitEntity));
                });
    }

    @Override
    public Optional<BusinessUnit> removeCollaborators(List<UUID> collaboratorIds, UUID id) {
        return this.businessUnityRepository.findById(id)
                .map(businessUnitEntity -> {
                    Set<CollaboratorEntity> collaboratorEntities = this.collaboratorRepository
                            .findByIdIn(collaboratorIds).stream().collect(Collectors.toSet());
                    businessUnitEntity.removeCollaborators(collaboratorEntities);
                    return businessUnityEntityToRecord(this.businessUnityRepository.save(businessUnitEntity));
                });
    }

    private Set<CollaboratorEntity> getValidCollaborators(Set<Collaborator> collaborators) {
        StringBuilder missingCollaborators = new StringBuilder();

        // Filtrar colaboradores válidos y acumular los faltantes
        Set<CollaboratorEntity> validCollaborators = collaborators.stream()
                .map(collaborator -> {
                    Optional<CollaboratorEntity> optionalCollaborator = collaboratorRepository
                            .findById(collaborator.id());
                    if (optionalCollaborator.isEmpty()) {
                        missingCollaborators.append(collaborator.id()).append(", ");
                        return null; // Retornar nulo para colaboradores faltantes
                    }
                    return optionalCollaborator.get();
                })
                .filter(Objects::nonNull) // Filtrar los nulos
                .collect(Collectors.toSet());

        // Si hay colaboradores faltantes, lanzar una excepción con todos los IDs
        if (missingCollaborators.length() > 0) {
            // Eliminar la última coma y espacio
            missingCollaborators.setLength(missingCollaborators.length() - 2);

            throw new NotFoundCollaboratorException("Collaborators not found in the database: " + missingCollaborators);
        }

        return validCollaborators;
    }

}
