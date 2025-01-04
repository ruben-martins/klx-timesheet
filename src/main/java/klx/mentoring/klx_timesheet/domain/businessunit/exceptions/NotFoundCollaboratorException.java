package klx.mentoring.klx_timesheet.domain.businessunit.exceptions;

public class NotFoundCollaboratorException extends RuntimeException{

    public NotFoundCollaboratorException(String message){
        super(message);
    }
}
