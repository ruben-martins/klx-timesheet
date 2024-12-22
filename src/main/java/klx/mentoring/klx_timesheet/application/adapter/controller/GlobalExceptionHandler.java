package klx.mentoring.klx_timesheet.application.adapter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("hireDate", "Hire date must be in the format yyyy-MM-dd.");
        return ResponseEntity.badRequest().body(errors);
    }
}
