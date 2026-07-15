package com.qa.automation.stepdefinitions.api;

import com.qa.automation.utils.ApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions for the API (users) feature.
 *
 * <p>Notice there is no browser/page here - API tests just send HTTP requests
 * via {@link ApiClient} and assert on the {@link Response}.
 */
public class ApiStepDefs {

    // We keep the last response so later steps can assert on it.
    private Response response;

    @Given("I set the API base URI")
    public void iSetTheApiBaseUri() {
        // Base URI is configured centrally in ApiClient / config.properties,
        // so there is nothing to do here - it is set automatically per request.
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String path) {
        response = ApiClient.get(path);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(),
                "Unexpected HTTP status code");
    }

    @Then("the response should contain {string} with value {string}")
    public void theResponseShouldContainWithValue(String key, String value) {
        // jsonPath reads a field from the JSON body, e.g. "page".
        String actual = response.jsonPath().getString(key);
        Assertions.assertEquals(value, actual,
                "Expected JSON field '" + key + "' to be '" + value + "' but was '" + actual + "'");
    }
}
