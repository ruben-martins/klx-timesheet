package klx.mentoring.klx_timesheet.application.adapter.controllers;

import klx.mentoring.klx_timesheet.domain.dto.CollaboratorDto;
import klx.mentoring.klx_timesheet.domain.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.records.CollaboratorRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorServicePort collaboratorService;

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
    public ResponseEntity<CollaboratorRecord> createCollaborator(@RequestBody CollaboratorDto collaborator) {
        CollaboratorRecord createdCollaborator = collaboratorService.create(collaborator);
        return new ResponseEntity<>(createdCollaborator, HttpStatus.CREATED);
    }

    // PUT method to update an existing collaborator
    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorRecord> updateCollaborator(@PathVariable UUID id, @RequestBody CollaboratorDto collaborator) {
        try {
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
    
}
