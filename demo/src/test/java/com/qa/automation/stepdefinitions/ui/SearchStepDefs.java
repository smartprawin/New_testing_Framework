package com.qa.automation.stepdefinitions.ui;

import com.qa.automation.pages.SearchPage;
import com.qa.automation.stepdefinitions.BaseStepDefinition;
import com.qa.automation.utils.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions for the Wikipedia search feature.
 *
 * <p>This shows the pattern for adding a NEW UI scenario:
 * 1. Extend {@link BaseStepDefinition} (so you get the {@code page} field).
 * 2. Create the page object in an @Before (after the base captured the page).
 * 3. Write @Given/@When/@Then methods that call the page object.
 */
public class SearchStepDefs extends BaseStepDefinition {

    private SearchPage searchPage;

    // Build the page object after capturing the page Hooks prepared (order 2).
    @Before(order = 3)
    public void initPage() {
        capturePage();                 // from BaseStepDefinition
        searchPage = new SearchPage(page);
    }

    @Given("I am on the Wikipedia home page")
    public void iAmOnTheWikipediaHomePage() {
        page.navigate(ConfigReader.get("ui.search.url"));
    }

    @When("I search for {string}")
    public void iSearchFor(String term) {
        searchPage.searchFor(term);
    }

    @Then("the search results should mention {string}")
    public void theSearchResultsShouldMention(String term) {
        // We check the heading OR the URL, because an exact match goes straight
        // to the article while a partial match shows a results page.
        boolean found = searchPage.getFirstHeading().toLowerCase().contains(term.toLowerCase())
                || page.url().toLowerCase().contains(term.toLowerCase());

        Assertions.assertTrue(found,
                "Expected search for '" + term + "' to show results, but URL was: " + page.url());
    }
}
