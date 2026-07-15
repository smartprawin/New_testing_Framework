# API test written in Gherkin. No browser is involved - just HTTP calls.

Feature: Users API
  As a consumer of the users service
  I want to retrieve user data
  So that I can display it in my client

  Scenario: Get list of users
    Given I set the API base URI
    When I send a GET request to "/api/users?page=2"
    Then the response status code should be 200
    And the response should contain "page" with value "2"
