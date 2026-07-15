package com.qa.automation.stepdefinitions.ui;

import com.qa.automation.pages.LoginPage;
import com.qa.automation.stepdefinitions.BaseStepDefinition;
import com.qa.automation.utils.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions for the login feature.
 *
 * <p>Each method is "glued" to a line in login.feature via the Gherkin keyword
 * and the text (use {string} / {int} to capture values from the feature file).
 *
 * <p>Extends {@link BaseStepDefinition} so we inherit the {@code page} field.
 */
public class LoginStepDefs extends BaseStepDefinition {

    private LoginPage loginPage;

    // Build the page object AFTER capturing the page Hooks prepared (order 2).
    @Before(order = 3)
    public void initPage() {
        capturePage();                 // from BaseStepDefinition
        loginPage = new LoginPage(page);
    }

    @Given("I navigate to the login page")
    public void iNavigateToTheLoginPage() {
        loginPage.goToLogin(ConfigReader.get("base.url"));
    }

    @When("I enter username {string} and password {string}")
    public void iEnterUsernameAndPassword(String username, String password) {
        loginPage.enterCredentials(username, password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.submit();
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        // Simple assertion: after a successful login we land on the /secure page.
        Assertions.assertTrue(loginPage.getCurrentUrl().contains("/secure"),
                "Expected to land on the secure area but was: " + loginPage.getCurrentUrl());
    }
}
