# API test written in Gherkin. No browser is involved - just HTTP calls.

@api
Feature: Users API
  As a consumer of the users service
  I want to retrieve user data
  So that I can display it in my client

  Scenario: Get a post
    Given I set the API base URI
    When I send a GET request to "/posts/1"
    Then the response status code should be 200
    And the response should contain "id" with value "1"
