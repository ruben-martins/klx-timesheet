package klx.mentoring.klx_timesheet.stepdefinitions;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import klx.mentoring.klx_timesheet.domain.collaborator.exceptions.InvalidCollaboratorDataException;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;
import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public class CollaboratorServiceExceptionStep extends StepDefsDefault{

    @Autowired
    CollaboratorServicePort collaboratorService;
    private Collaborator invalidCollaborator;
    private String responseBody;

    @Given("A collaborator with a null name")
    public void a_collaborator_with_a_null_name() {
        invalidCollaborator = new Collaborator(
                null,    // UUID.randomUUID(),
                null,       // Null name
                "Doe",
                "john.doe@example.com",
                LocalDate.of(2020, 1, 1),
                "Developer"
        );
    }

    @When("The collaborator service attempts to create it")
    public void the_collaborator_service_attempts_to_create_it() {
        InvalidCollaboratorDataException exception = assertThrows(InvalidCollaboratorDataException.class, () -> {
           collaboratorService.create(invalidCollaborator);
        });
        responseBody = exception.getMessage();
    }

    @Then("The InvalidCollaboratorDataException is threw")
    public void the_InvalidCollaboratorDataException_is_threw() {
        // Verify that the error message contains the expected message from GlobalExceptionHandler
        assertTrue("Expected error message to contain validation errors, but got: " + responseBody,
                responseBody.contains("The collaborator cannot be created because its data has errors"));

        // Optional: Verify that the specific error on the name is also present
        assertTrue("Expected error message to mention invalid name, but got: " + responseBody,
                responseBody.contains("Name must be between 2 and 100 characters and cannot be null or empty."));
    }
}
