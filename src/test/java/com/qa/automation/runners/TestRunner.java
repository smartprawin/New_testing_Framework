package com.qa.automation.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * This is the single entry point for running the tests.
 *
 * <p>With Cucumber + JUnit 5 you don't write a main() method. Instead you mark
 * a class with {@code @Suite} and tell JUnit to delegate to the "cucumber"
 * engine. Run this class (or {@code mvn test}) and Cucumber will find and
 * execute every {@code .feature} file.
 *
 * <p>Key settings:
 * <ul>
 *   <li>{@code @SelectClasspathResource("features")} -> where the .feature files live.</li>
 *   <li>{@code GLUE_PROPERTY_NAME} -> the Java package that contains step definitions & hooks.</li>
 *   <li>{@code PLUGIN_PROPERTY_NAME} -> how results are reported (console + HTML + JSON).</li>
 * </ul>
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.qa.automation")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-report.html, json:target/cucumber.json")
public class TestRunner {
    // No code needed; this class is just configuration.
}
