package klx.mentoring.klx_timesheet.domain.adapter.services;

import klx.mentoring.klx_timesheet.domain.exceptions.CollaboratorNotFoundException;
import klx.mentoring.klx_timesheet.domain.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.ports.repositories.CollaboratorRepositoryPort;
import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;

import java.util.List;
import java.util.UUID;

public class CollaboratorServiceImpl implements CollaboratorServicePort {

    private final CollaboratorRepositoryPort collaboratorRepository;

    public CollaboratorServiceImpl(CollaboratorRepositoryPort collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    @Override
    public List<CollaboratorRecord> findAll() {
        List<CollaboratorRecord> collaborators = this.collaboratorRepository.findAll();
        return collaborators;
    }

    @Override
    public CollaboratorRecord findById(UUID id) {
        // Validamos que el ID no sea null
        validateId(id);

        // Usamos el Optional devuelto por el repositorio para manejar la ausencia del colaborador
        return this.collaboratorRepository.findById(id)
            .orElseThrow(() -> new CollaboratorNotFoundException("Collaborator with ID " + id + " not found"));
    }

    @Override
    public CollaboratorRecord create(CollaboratorRecord collaborator) {
        CollaboratorRecord savedCollaborator = this.collaboratorRepository.create(collaborator);
        return savedCollaborator;
    }

    @Override
    public CollaboratorRecord update(UUID id, CollaboratorRecord collaborator) {
        validateId(id);
        validateCollaborator(collaborator);

        // Verificamos la existencia del colaborador y actualizamos en un solo flujo
        return collaboratorRepository.findById(id)
            .map(existingCollaborator -> collaboratorRepository.update(id, collaborator)
                .orElseThrow(() -> new CollaboratorNotFoundException("Failed to update collaborator with ID " + id))
            )
            .orElseThrow(() -> new CollaboratorNotFoundException("Collaborator with ID " + id + " not found"));
    }

   @Override
    public void deleteById(UUID id) {
    // Validamos que el ID no sea nulo
        validateId(id);

        // Verificamos si el colaborador existe antes de intentar eliminarlo
        collaboratorRepository.findById(id)
            .orElseThrow(() -> new CollaboratorNotFoundException("Collaborator with ID " + id + " not found"));

        // Si existe, procedemos con la eliminación
        collaboratorRepository.deleteById(id);
    }

    // Método auxiliar para validar que el ID no sea null
    private void validateId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Collaborator ID must not be null");
        }
    }

    // Método auxiliar para validar que el objeto CollaboratorRecord no sea null
    private void validateCollaborator(CollaboratorRecord collaborator) {
        if (collaborator == null) {
            throw new IllegalArgumentException("Collaborator record must not be null");
        }
    }
    
}
