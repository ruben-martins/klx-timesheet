package klx.mentoring.klx_timesheet.stepdefinitions;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public class CollaboratorExceptionStep extends StepDefsDefault{

    @Autowired
    private TestRestTemplate testRestTemplate;
    private Collaborator invalidCollaborator;
    private HttpStatus responseStatus;
    private String responseBody;

    @Given("I have a collaborator with a null name")
    public void i_have_a_collaborator_with_a_null_name() {
        invalidCollaborator = new Collaborator(
                null,    // UUID.randomUUID(),
                null,       // Null name
                "Doe",
                "john.doe@example.com",
                LocalDate.of(2020, 1, 1),
                "Developer"
        );
    }

    @When("I attempt to create the collaborator")
    public void i_attempt_to_create_the_collaborator() {
        try {
            ResponseEntity<String> responseEntity = testRestTemplate.postForEntity( 
                new URI("/api/collaborators"), invalidCollaborator, String.class);
            responseStatus = HttpStatus.valueOf(responseEntity.getStatusCode().value());
            responseBody = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            responseStatus = HttpStatus.valueOf(e.getStatusCode().value());
            responseBody = e.getResponseBodyAsString();
        } catch (Exception e) {
        }
    }

    @Then("I receive an InvalidCollaboratorDataException")
    public void i_receive_an_invalid_collaborator_data_exception() {
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus, "Expected status 400 BAD REQUEST");

        // Verify that the error message contains the expected message from GlobalExceptionHandler
        assertTrue("Expected error message to contain validation errors, but got: " + responseBody,
            responseBody.contains("The collaborator cannot be created because its data has errors"));

        // Optional: Verify that the specific error on the name is also present
        assertTrue("Expected error message to mention invalid name, but got: " + responseBody, 
            responseBody.contains("Name must be between 2 and 100 characters and cannot be null or empty."));     
    }

}