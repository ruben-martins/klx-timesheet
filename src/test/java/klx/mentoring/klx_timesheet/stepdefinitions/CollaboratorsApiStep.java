package klx.mentoring.klx_timesheet.stepdefinitions;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollaboratorsApiStep extends StepDefsDefault {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ResponseEntity<Collaborator[]> response;

    private final List<Collaborator> collaboratorsList = new ArrayList<>();

    @Given("there are registered employees in the system:")
    public void collaboratorsRegisteredInTheSystem(DataTable dataTable) throws URISyntaxException {
        List<Map<String, Object>> collaborators = dataTable.asMaps(String.class, Object.class);
        for (Map<String, Object> collaboratorData : collaborators) {
            Collaborator collaborator = new Collaborator(  null, 
                                    (String) collaboratorData.get("name"), 
                                    (String) collaboratorData.get("lastName"),
                                    (String) collaboratorData.get("email"), 
                                    LocalDate.parse((String) collaboratorData.get("hireDate")),
                                    (String) collaboratorData.get("position"));
            ResponseEntity<Collaborator> responseEntity = testRestTemplate.postForEntity(
                    new URI("/api/collaborators"), collaborator, Collaborator.class);
            collaboratorsList.add(responseEntity.getBody());
        }
    }

    @When("I make a GET request to retrieve employees")
    public void getRequestTo() throws URISyntaxException {
//        String jsonResponse = testRestTemplate.getForObject(new URI("/api/collaborators"), String.class);
//        System.out.println("Response JSON: " + jsonResponse);
        response = testRestTemplate.getForEntity(new URI("/api/collaborators"), Collaborator[].class);
    }

    @Then("the response should have status code {int}")
    public void statusCodeResponse(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @And("the response should contain a list of employees")
    public void userListcontainedInResponse() {
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    @And("the response should contain a list of employees with the following data")
    public void theFollowingDataMustBeContainedInResponse(DataTable dataTable) {
        System.out.println(dataTable);
    }

    @And("the response should include the previously registered data")
    public void dataEnrolledPreviouslyMustBeContainedInResponse() {
        collaboratorsList.forEach(collaborator -> {
            List<Collaborator> collaboratorsResponse = Arrays.stream(response.getBody()).toList();
            assertTrue(collaboratorsResponse.contains(collaborator));
        });
    }
}
