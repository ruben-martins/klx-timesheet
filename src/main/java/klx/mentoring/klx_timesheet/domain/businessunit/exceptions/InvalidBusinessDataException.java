package klx.mentoring.klx_timesheet.domain.businessunit.exceptions;

import java.util.HashMap;
import java.util.Map;

public class InvalidBusinessDataException extends RuntimeException{

    private Map<String, String> errorMap = new HashMap<>();
    
    public InvalidBusinessDataException(String message, Map<String, String> errors){
        super(message);
        this.errorMap = errors;
    }

    @Override
    public String getMessage() {
        StringBuilder errorMessage = new StringBuilder(super.getMessage());
        if (!errorMap.isEmpty()) {
            // errorMessage.append(" errors: ");
            errorMap.forEach((field, error) -> 
                errorMessage.append("\n - ").append(field).append(": ").append(error)
            );
        }
        return errorMessage.toString();
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
