package klx.mentoring.klx_timesheet.application.adapter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klx.mentoring.klx_timesheet.domain.businessunit.exceptions.NotFoundCollaboratorException;
import klx.mentoring.klx_timesheet.domain.businessunit.model.BusinessUnit;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.service.BusinessUnitServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

@RestController
@RequestMapping("/api/bus")
public class BusinessUnitController {

    @Autowired
    BusinessUnitServicePort service;

    @GetMapping
    public ResponseEntity<List<BusinessUnit>> getAllbusinessUnits() {
        List<BusinessUnit> businessUnits = service.findAll();
        return new ResponseEntity<>(businessUnits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnit> getBusinessUnitById(@PathVariable UUID id) {
        Optional<BusinessUnit> foundBusinessUnit = service.findById(id);
        if(foundBusinessUnit.isPresent()){
            return ResponseEntity.ok(foundBusinessUnit.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createBusinessUnit(@RequestBody BusinessUnit businessUnit) {
        Map<String, String> errors = businessUnit.validate();
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(businessUnit));
        } catch (NotFoundCollaboratorException e) {
            errors.put("collaborators", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        } catch (IllegalArgumentException e) {
            errors.put("collaborators", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBusinessUnit(@RequestBody BusinessUnit businessUnit, @PathVariable UUID id) {
        Map<String, String> errors = businessUnit.validate();
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            Optional<BusinessUnit> updatedBusinessUnit = this.service.update(businessUnit, id);
            return updatedBusinessUnit.map(c-> ResponseEntity.ok(c))
                            .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NotFoundCollaboratorException e) {
            errors.put("collaborators", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        } catch (IllegalArgumentException e) {
            errors.put("collaborators", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBusinessEntity(@PathVariable UUID id) {
        Optional<BusinessUnit> deletedBusinessUnit = service.deleteById(id);
        if ( deletedBusinessUnit.isPresent()) {
            return ResponseEntity.ok(deletedBusinessUnit.get());
        }
        return ResponseEntity.notFound().build();
    } 

    @PutMapping("/collaborators/add/{id}")
    public ResponseEntity<?> addCollaboratorsToBusinessEntity(@RequestBody Set<Collaborator> collaborators, @PathVariable UUID id) {
        Map<String, String> errors = new HashMap<>();
        try {
            Optional<BusinessUnit> addedBusinessUnits = this.service.addCollaborators(collaborators, id);
            return addedBusinessUnits.map(c-> ResponseEntity.ok(c))
                            .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NotFoundCollaboratorException e) {
            errors.put("collaborators", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }

    @PutMapping("/collaborators/remove/{id}")
    public ResponseEntity<?> removeCollaboratorsToBusinessEntity(@RequestBody Set<Collaborator> collaborators, @PathVariable UUID id) {
        Map<String, String> errors = new HashMap<>();
        try {
            Optional<BusinessUnit> removedBusinessUnits = this.service.removeCollaborators(collaborators, id);
            return removedBusinessUnits.map(c-> ResponseEntity.ok(c))
                            .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NotFoundCollaboratorException e) {
            errors.put("collaborators", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }
    
}
