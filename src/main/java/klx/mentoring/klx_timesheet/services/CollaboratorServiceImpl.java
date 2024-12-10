package klx.mentoring.klx_timesheet.services;

import klx.mentoring.klx_timesheet.models.Collaborator;
import klx.mentoring.klx_timesheet.records.CollaboratorRecord;
import klx.mentoring.klx_timesheet.repositories.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Override
    public List<CollaboratorRecord> findAll() {
        return collaboratorRepository.findAll()
                .stream()
                .map(this::toRecord)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CollaboratorRecord> findById(Long id) {
        return collaboratorRepository.findById(id)
                .map(this::toRecord);
    }

    @Override
    public CollaboratorRecord create(Collaborator collaborator) {
        collaborator.setHireDate(LocalDate.now());
        Collaborator savedCollaborator = collaboratorRepository.save(collaborator);
        return toRecord(savedCollaborator);
    }

    @Override
    public CollaboratorRecord update(Long id, Collaborator collaborator) {
        Optional<Collaborator> existingCollaborator = collaboratorRepository.findById(id);
        if (existingCollaborator.isPresent()) {
            Collaborator updatedCollaborator = existingCollaborator.get();
            updatedCollaborator.setName(collaborator.getName());
            updatedCollaborator.setLastName(collaborator.getLastName());
            updatedCollaborator.setEmail(collaborator.getEmail());
            updatedCollaborator.setHireDate(collaborator.getHireDate());
            updatedCollaborator.setPosition(collaborator.getPosition());
            return toRecord(collaboratorRepository.save(updatedCollaborator));
        } else {
            throw new RuntimeException("Collaborator not found with id: " + id);
        }
    }

    @Override
    public void delete(Long id) {
        collaboratorRepository.deleteById(id);
    }

    // Método de conversión de Collaborator a CollaboratorRecord
    private CollaboratorRecord toRecord(Collaborator collaborator) {
        return new CollaboratorRecord(
                collaborator.getId(),
                collaborator.getName(),
                collaborator.getLastName(),
                collaborator.getEmail(),
                collaborator.getHireDate(),
                collaborator.getPosition()
        );
    }
}
