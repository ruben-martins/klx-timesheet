package klx.mentoring.klx_timesheet.application.adapter.controllers;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorServicePort service;

    @Autowired
    private Validator validator;

    // GET method to retrieve all collaborators
    @GetMapping
    public ResponseEntity<List<Collaborator>> getAllCollaborators() {
        List<Collaborator> collaborators = service.findAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

    // GET method to retrieve a single collaborator by ID
    @GetMapping("/{id}")
    public ResponseEntity<Collaborator> getCollaboratorById(@PathVariable UUID id) {
        Optional<Collaborator> foundCollaborator = service.findById(id);
        if(foundCollaborator.isPresent()){
            return ResponseEntity.ok(foundCollaborator.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // POST method to create a new collaborator
    @PostMapping
    public ResponseEntity<?> createCollaborator(@RequestBody Collaborator collaborator) {
        // Convert Record to Entity
        CollaboratorEntity collaboratorEntity = toEntity(collaborator);

        // Validate the entity
        Set<jakarta.validation.ConstraintViolation<CollaboratorEntity>> violations = validator.validate(collaboratorEntity);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(collaborator));
    }

    // PUT method to update an existing collaborator
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCollaborator(@RequestBody Collaborator collaborator, @PathVariable UUID id) {
        // Convert Record to Entity
        CollaboratorEntity collaboratorEntity = toEntity(collaborator);

        // Validate the entity
        Set<jakarta.validation.ConstraintViolation<CollaboratorEntity>> violations = validator.validate(collaboratorEntity);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList());
        }

        Optional<Collaborator> updatedCollaborator = service.update(id, collaborator);
        if (updatedCollaborator.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCollaborator.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    // DELETE method to remove a collaborator by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable UUID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private CollaboratorEntity toEntity(Collaborator record) {
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
