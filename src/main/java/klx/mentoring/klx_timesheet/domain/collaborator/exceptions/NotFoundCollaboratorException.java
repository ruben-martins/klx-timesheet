package klx.mentoring.klx_timesheet.domain.collaborator.exceptions;

public class NotFoundCollaboratorException extends RuntimeException{

    public NotFoundCollaboratorException(String message){
        super(message);
    }
}
