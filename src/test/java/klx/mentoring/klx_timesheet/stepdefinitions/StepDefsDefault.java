package klx.mentoring.klx_timesheet.stepdefinitions;

import klx.mentoring.klx_timesheet.KlxTimesheetApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = KlxTimesheetApplication.class)
public class StepDefsDefault {
}
