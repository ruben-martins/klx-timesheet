package klx.mentoring.klx_timesheet.application.adapter.controller;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorServicePort service;

    @GetMapping
    public ResponseEntity<List<Collaborator>> getAllCollaborators() {
        List<Collaborator> collaborators = service.findAll();
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collaborator> getCollaboratorById(@PathVariable UUID id) {
        Optional<Collaborator> foundCollaborator = service.findById(id);
        if(foundCollaborator.isPresent()){
            return ResponseEntity.ok(foundCollaborator.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Collaborator> createCollaborator(@RequestBody Collaborator collaborator) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(collaborator));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Collaborator> updateCollaborator(@RequestBody Collaborator collaborator, @PathVariable UUID id) {
        Optional<Collaborator> updatedCollaborator = service.update(id, collaborator);
        return updatedCollaborator.map(c-> ResponseEntity.ok(c))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
            
    @DeleteMapping("/{id}")
    public ResponseEntity<Collaborator> deleteCollaborator(@PathVariable UUID id) {
        Optional<Collaborator> deletedCollaborator = service.deleteById(id);
        if (deletedCollaborator.isPresent()) {
            return ResponseEntity.ok(deletedCollaborator.get());
        }
        return ResponseEntity.notFound().build();
    }

}
