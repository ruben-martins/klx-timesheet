package klx.mentoring.klx_timesheet.infrastructure.collaborator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import klx.mentoring.klx_timesheet.domain.collaborator.adapter.service.CollaboratorServiceImpl;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence.CollaboratorRepositoryPort;

@Configuration
public class CollaboratorConfig {

    @Bean
    CollaboratorServicePort CollaboratorService(CollaboratorRepositoryPort collaboratorRepositoryPort) {
        return new CollaboratorServiceImpl(collaboratorRepositoryPort);
    }
}
