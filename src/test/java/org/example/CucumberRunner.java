package org.example;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"org.example.utils.MyAllureCucumberListner"},
        glue = {"org/example/steps"},
        features = {"src/test/resources/"},
        tags = {"@firstTest"}
)
public class CucumberRunner {}