package klx.mentoring.klx_timesheet.application.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            processInvalidFormatException((InvalidFormatException) cause, errors);
        } else {
            errors.put("collaborators", "Invalid value for collaborators. Must be a valid set of Collaborators.");
        }
        return errors;
    }

    private void processInvalidFormatException(InvalidFormatException ex, Map<String, String> errors) {
        String fullPath = ex.getPath().stream()
            .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
            .reduce((prev, next) -> prev + "." + next)
            .orElse("unknown");

        String fieldName = ex.getPath().isEmpty() ? "unknown" : ex.getPath().get(ex.getPath().size() - 1).getFieldName();
        Object invalidValue = ex.getValue();

        switch (fieldName) {
            case "hireDate":
                errors.put("hireDate", "Hire date must be in the format yyyy-MM-dd.");
                break;
            case "id":
                errors.put("collaborators", "Invalid ID format: '" + invalidValue + "' at path '" + fullPath + "'. Collaborator ID must be a valid UUID.");
                break;
            default:
            String causeMessage = ex.getMessage() != null ? (ex.getMessage().contains("\n") 
                ? ex.getMessage().substring(0, ex.getMessage().indexOf("\n")) 
                : ex.getMessage()) // Si no hay '\n', usar todo el mensaje
                : "Invalid value.";
            errors.put("collaborators", causeMessage);
        }
    }
}
