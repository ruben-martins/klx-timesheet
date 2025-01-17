package klx.mentoring.klx_timesheet.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import klx.mentoring.klx_timesheet.domain.businessunit.ports.persistence.BusinessUnitRepositoryPort;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.service.BusinessUnitServicePort;
import klx.mentoring.klx_timesheet.domain.businessunit.service.BusinessUnitServiceImpl;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.interfaces.CollaboratorServicePort;
import klx.mentoring.klx_timesheet.domain.collaborator.ports.persistence.CollaboratorRepositoryPort;
import klx.mentoring.klx_timesheet.domain.collaborator.service.CollaboratorServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    CollaboratorServicePort CollaboratorService(CollaboratorRepositoryPort collaboratorRepositoryPort) {
        return new CollaboratorServiceImpl(collaboratorRepositoryPort);
    }

    @Bean
    BusinessUnitServicePort BusinessService(BusinessUnitRepositoryPort businessUnitRepositoryPort,
            CollaboratorRepositoryPort collaboratorRepositoryPort) {
        return new BusinessUnitServiceImpl(businessUnitRepositoryPort, collaboratorRepositoryPort);
    }
}
