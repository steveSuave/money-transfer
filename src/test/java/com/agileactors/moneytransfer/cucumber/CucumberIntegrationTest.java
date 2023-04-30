package com.agileactors.moneytransfer.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:Feature/", glue = "com.agileactors.moneytransfer.cucumber")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CucumberIntegrationTest {}
