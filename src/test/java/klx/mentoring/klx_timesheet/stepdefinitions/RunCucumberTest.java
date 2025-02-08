package klx.mentoring.klx_timesheet.stepdefinitions;

import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@CucumberOptions(
        glue = "klx.mentoring.klx_timesheet.stepdefinitions" // Ruta a tus definiciones de pasos
)
@SelectPackages("features")
public class RunCucumberTest {
}
