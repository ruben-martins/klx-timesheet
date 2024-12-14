package klx.mentoring.klx_timesheet.domain.exceptions;

public class CollaboratorNotFoundException extends RuntimeException {
    public CollaboratorNotFoundException(String message) {
        super(message);
    }
}