package klx.mentoring.klx_timesheet.controllers;

import klx.mentoring.klx_timesheet.records.CollaboratorRecord;
import klx.mentoring.klx_timesheet.models.Collaborator;
import klx.mentoring.klx_timesheet.services.CollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    // GET method to retrieve all collaborators
    @GetMapping
    public ResponseEntity<List<CollaboratorRecord>> getAllCollaborators() {
        List<CollaboratorRecord> collaborators = collaboratorService.findAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

    // GET method to retrieve a single collaborator by ID
    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorRecord> getCollaboratorById(@PathVariable Long id) {
        Optional<CollaboratorRecord> collaborator = collaboratorService.findById(id);
        return collaborator
                .map(record -> new ResponseEntity<>(record, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST method to create a new collaborator
    @PostMapping
    public ResponseEntity<CollaboratorRecord> createCollaborator(@RequestBody Collaborator collaborator) {
        CollaboratorRecord createdCollaborator = collaboratorService.create(collaborator);
        return new ResponseEntity<>(createdCollaborator, HttpStatus.CREATED);
    }

    // PUT method to update an existing collaborator
    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorRecord> updateCollaborator(@PathVariable Long id, @RequestBody Collaborator collaborator) {
        try {
            CollaboratorRecord updatedCollaborator = collaboratorService.update(id, collaborator);
            return new ResponseEntity<>(updatedCollaborator, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE method to remove a collaborator by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable Long id) {
        collaboratorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
