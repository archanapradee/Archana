package StepDefinitions;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features="src\\test\\resources\\Features",glue={"StepDefinitions"},
       monochrome = true,
        plugin = { "pretty", "html:target/cucumber-reports/report.html"})
public class TestRunner {
// To generate the HTML pretty report and this is the runner class
}
