# Feature file written in Gherkin (plain English).
# Each "Scenario" line becomes test steps; the Java methods in
# LoginStepDefs are glued to these lines by their text.

@ui
Feature: Login functionality
  As a registered user
  I want to log in to the application
  So that I can access the dashboard

  # A Scenario is one test case.
  Scenario: Successful login with valid credentials
    # Gherkin steps below map to @Given / @When / @Then methods.
    Given I navigate to the login page
    When I enter username "tomsmith" and password "SuperSecretPassword!"
    And I click the login button
    Then I should be redirected to the dashboard
