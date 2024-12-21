package klx.mentoring.klx_timesheet.application.adapter.controllers;

import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.record.CollaboratorRecord;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorServicePort collaboratorService;

    @Autowired
    private Validator validator;

    // GET method to retrieve all collaborators
    @GetMapping
    public ResponseEntity<List<CollaboratorRecord>> getAllCollaborators() {
        List<CollaboratorRecord> collaborators = collaboratorService.findAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

    // GET method to retrieve a single collaborator by ID
    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorRecord> getCollaboratorById(@PathVariable UUID id) {
        CollaboratorRecord collaborator = collaboratorService.findById(id);
        if(collaborator != null){
            return new ResponseEntity<>(collaborator, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST method to create a new collaborator
    @PostMapping
    public ResponseEntity<?> createCollaborator(@RequestBody CollaboratorRecord collaborator) {
        // Convert Record to Entity
        CollaboratorEntity collaboratorEntity = toEntity(collaborator);

        // Validate the entity
        Set<jakarta.validation.ConstraintViolation<CollaboratorEntity>> violations = validator.validate(collaboratorEntity);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList());
        }

        // Proceed with the service call
        CollaboratorRecord createdCollaborator = collaboratorService.create(collaborator);
        return new ResponseEntity<>(createdCollaborator, HttpStatus.CREATED);
    }

    // PUT method to update an existing collaborator
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCollaborator(@PathVariable UUID id, @RequestBody CollaboratorRecord collaborator) {
        // Convert Record to Entity
        CollaboratorEntity collaboratorEntity = toEntity(collaborator);

        // Validate the entity
        Set<jakarta.validation.ConstraintViolation<CollaboratorEntity>> violations = validator.validate(collaboratorEntity);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList());
        }

        try {
            // Proceed with the service call
            CollaboratorRecord updatedCollaborator = collaboratorService.update(id, collaborator);
            return new ResponseEntity<>(updatedCollaborator, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // DELETE method to remove a collaborator by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable UUID id) {
        collaboratorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private CollaboratorEntity toEntity(CollaboratorRecord record) {
        return new CollaboratorEntity(
            record.id(),
            record.name(),
            record.lastName(),
            record.email(),
            record.hireDate(),
            record.position()
        );
    }
}
