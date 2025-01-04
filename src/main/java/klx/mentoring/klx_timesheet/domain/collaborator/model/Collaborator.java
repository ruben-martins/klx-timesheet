package klx.mentoring.klx_timesheet.domain.collaborator.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public record Collaborator(
    UUID id,
    String name,
    String lastName,
    String email,
    LocalDate hireDate,
    String position
) {

    public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<>();
        validateName(errors);
        validateLastName(errors);
        validateEmail(errors);
        validateHireDate(errors);
        validatePosition(errors);
        return errors;
    }

    private void validateName(Map<String, String> errors) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 100) {
            errors.put("name", "Name must be between 2 and 100 characters and cannot be null or empty.");
        }
    }

    private void validateLastName(Map<String, String> errors) {
        if (lastName == null || lastName.trim().isEmpty() || lastName.length() < 2 || lastName.length() > 100) {
            errors.put("lastName", "Last name must be between 2 and 100 characters and cannot be null or empty.\"");
        }
    }

    private void validateEmail(Map<String, String> errors) {
        if (email == null || !email.contains("@") || email.trim().isEmpty()) {
            errors.put("email", "Email must be valid and cannot be null or empty.");
        }
    }

    private void validateHireDate(Map<String, String> errors) {
        if (hireDate == null) {
            errors.put("hireDate", "Hire date cannot be null.");
            return;
        }

        try {
            // Verificar si hireDate tiene un formato válido
            LocalDate parsedDate = LocalDate.parse(hireDate.toString());

            // Validar si la fecha está en el futuro
            if (parsedDate.isAfter(LocalDate.now())) {
                errors.put("hireDate", "Hire date must be in the past or today.");
            }
        } catch (Exception e) {
            errors.put("hireDate", "Hire date format is invalid.");
        }
    }

    private void validatePosition(Map<String, String> errors) {
        if (position == null || position.trim().isEmpty() || position.length() < 2 || position.length() > 100) {
            errors.put("position", "Position must be between 2 and 100 characters and cannot be null or empty.");
        }
    }
}