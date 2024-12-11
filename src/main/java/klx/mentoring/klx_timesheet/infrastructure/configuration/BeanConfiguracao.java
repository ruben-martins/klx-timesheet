package klx.mentoring.klx_timesheet.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import klx.mentoring.klx_timesheet.domain.adapter.services.RequestServiceImpl;
import klx.mentoring.klx_timesheet.domain.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.ports.repositories.CollaboratorRepositoryPort;

@Configuration
public class BeanConfiguracao {

    @Bean
    CollaboratorServicePort CollaboratorService(CollaboratorRepositoryPort collaboratorRepositoryPort) {
        return new RequestServiceImpl(collaboratorRepositoryPort);
    }
}
