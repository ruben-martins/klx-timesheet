package klx.mentoring.klx_timesheet.infrastructure.businessunit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import klx.mentoring.klx_timesheet.domain.businessunit.ports.persistence.BusinessUnitRepositoryPort;
import klx.mentoring.klx_timesheet.domain.businessunit.ports.service.BusinessUnitServicePort;
import klx.mentoring.klx_timesheet.domain.businessunit.adapter.service.BusinessUnitServiceImpl;


@Configuration
public class BusinessUnitConfig {

    @Bean
    BusinessUnitServicePort BusinessService(BusinessUnitRepositoryPort businessUnitRepositoryPort) {
        return new BusinessUnitServiceImpl(businessUnitRepositoryPort);
    }
}
