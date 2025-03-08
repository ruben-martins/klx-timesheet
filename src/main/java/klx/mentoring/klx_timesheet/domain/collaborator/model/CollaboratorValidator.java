package klx.mentoring.klx_timesheet.domain.collaborator.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public final class CollaboratorValidator {

    private CollaboratorValidator() {
        // Private constructor to prevent instantiation
    }

    public static Map<String, String> validate(Collaborator collaborator) {
        Map<String, String> errors = new HashMap<>();
        validateName(collaborator.name(), errors);
        validateLastName(collaborator.lastName(), errors);
        validateEmail(collaborator.email(), errors);
        validateHireDate(collaborator.hireDate(), errors);
        validatePosition(collaborator.position(), errors);
        return errors;
    }

    private static void validateName(String name, Map<String, String> errors) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 100) {
            errors.put("name", "Name must be between 2 and 100 characters and cannot be null or empty.");
        }
    }

    private static void validateLastName(String lastName, Map<String, String> errors) {
        if (lastName == null || lastName.trim().isEmpty() || lastName.length() < 2 || lastName.length() > 100) {
            errors.put("lastName", "Last name must be between 2 and 100 characters and cannot be null or empty.");
        }
    }

    private static void validateEmail(String email, Map<String, String> errors) {
        if (email == null || !email.contains("@") || email.trim().isEmpty()) {
            errors.put("email", "Email must be valid and cannot be null or empty.");
        }
    }

    private static void validateHireDate(LocalDate hireDate, Map<String, String> errors) {
        if (hireDate == null) {
            errors.put("hireDate", "Hire date cannot be null.");
            return;
        }

        if (hireDate.isAfter(LocalDate.now())) {
            errors.put("hireDate", "Hire date must be in the past or today.");
        }
    }

    private static void validatePosition(String position, Map<String, String> errors) {
        if (position == null || position.trim().isEmpty() || position.length() < 2 || position.length() > 100) {
            errors.put("position", "Position must be between 2 and 100 characters and cannot be null or empty.");
        }
    }
}
