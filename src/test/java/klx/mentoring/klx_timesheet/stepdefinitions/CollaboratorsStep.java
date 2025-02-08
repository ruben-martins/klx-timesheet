package klx.mentoring.klx_timesheet.stepdefinitions;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.persistence.CollaboratorRepository;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

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

public class CollaboratorsStep extends StepDefsDefault {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    private ResponseEntity<Collaborator[]> response;

    private final List<Collaborator> collaboratorsList = new ArrayList<>();

    @Dado("que existem colaboradores cadastrados no sistema:")
    public void queExistemColaboradoresCadastradosNoSistema(DataTable dataTable) {
        List<Map<String, Object>> collaborators = dataTable.asMaps(String.class, Object.class);
        for (Map<String, Object> collaboratorData : collaborators) {
            Collaborator collaborator = new Collaborator(  null, 
                                    (String) collaboratorData.get("name"), 
                                    (String) collaboratorData.get("lastName"),
                                    (String) collaboratorData.get("email"), 
                                    LocalDate.of(2024, 01, 01),
                                    (String) collaboratorData.get("position"));
            collaboratorsList.add(collaboratorRepository.create(collaborator));
        }
    }

    @Quando("eu faço uma requisição GET para obter colaboradores")
    public void euFaçoUmaRequisiçãoGETPara() throws URISyntaxException {
        String jsonResponse = testRestTemplate.getForObject(new URI("/api/collaborators"), String.class);
        System.out.println("Response JSON: " + jsonResponse);
        response = testRestTemplate.getForEntity(new URI("/api/collaborators"), Collaborator[].class);
    }

    @Então("a resposta deve ter o status code {int}")
    public void aRespostaDeveTerOStatusCode(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @E("a resposta deve conter uma lista de colaboradores")
    public void aRespostaDeveConterUmaListaDeUsuários() {
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    @E("a resposta deve conter uma lista de colaboradores com os seguintes dados")
    public void aRespostaDeveConterUmaListaDeUsuáriosComOsSeguintesDados(DataTable dataTable) {
        System.out.println(dataTable);
    }

    @E("a resposta deve conter os dados que foram cadastrados previamente")
    public void aRespostaDeveConterOsDadosQueForamCadastradosPreviamente() {
        collaboratorsList.forEach(collaborator -> {
            List<Collaborator> collaboratorsResponse = Arrays.stream(response.getBody()).toList();
            assertTrue(collaboratorsResponse.contains(collaborator));
        });
    }
}
