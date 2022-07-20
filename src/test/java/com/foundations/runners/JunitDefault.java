package com.foundations.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore and @test",
        features = "src/test/resources/features",
        glue = {"com.foundations"}
)
public class JunitDefault {
}
