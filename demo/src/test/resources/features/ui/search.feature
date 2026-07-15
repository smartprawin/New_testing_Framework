# Second UI example: a search flow on Wikipedia.
# This shows how a new scenario is built from a feature file + step defs + page object.

@ui
Feature: Wikipedia search
  As a curious user
  I want to search for a topic
  So that I can read about it

  Scenario: Search for a topic
    Given I am on the Wikipedia home page
    When I search for "Playwright"
    Then the search results should mention "Playwright"
