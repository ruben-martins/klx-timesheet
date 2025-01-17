package klx.mentoring.klx_timesheet.domain.businessunit.model;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.utils.CollaboratorsDeserializer;


public record BusinessUnit(
    UUID id, 
    String name, 
    @JsonDeserialize(using = CollaboratorsDeserializer.class)
    Set<Collaborator> collaborators) {

    public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<>();
        validateName(errors);
        validateCollaborators(errors);
        return errors;
    }

    private void validateName(Map<String, String> errors) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 100) {
            errors.put("name", "Name must be between 2 and 100 characters and cannot be null or empty.");
        }
    }

    private void validateCollaborators(Map<String, String> errors) {
        if (collaborators == null) {
            errors.put("collaborators", "Collaborators cannot be null.");
            return;
        }
    
        for (Collaborator collaborator : collaborators) {
            if (collaborator.id() == null) {
                errors.put("collaborators", "Each collaborator must have a valid ID. Found a collaborator without ID.");
                break;
            }
        }
    }

}
