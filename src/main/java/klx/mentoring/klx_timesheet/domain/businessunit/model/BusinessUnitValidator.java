package klx.mentoring.klx_timesheet.domain.businessunit.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public final class BusinessUnitValidator {

    private BusinessUnitValidator(){}

    public static Map<String, String> validate(BusinessUnit businessUnit) {
        Map<String, String> errors = new HashMap<>();
        validateName(businessUnit.name(), errors);
        validateCollaborators(businessUnit.collaborators(), errors);
        return errors;
    }

    private static void validateName(String name, Map<String, String> errors) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 100) {
            errors.put("name", "Name must be between 2 and 100 characters and cannot be null or empty.");
        }
    }

    private static void validateCollaborators(Set<Collaborator> collaborators, Map<String, String> errors) {
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
