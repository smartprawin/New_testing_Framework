package com.qa.automation.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Thin wrapper around Rest Assured so all API calls share the same setup
 * (base URL, headers, logging). Keeping this in one place means step
 * definitions stay short and consistent.
 *
 * <p>Rest Assured basics:
 * <ul>
 *   <li>{@code given()}  -> build the request (URL, headers, body).</li>
 *   <li>{@code when()}   -> choose the HTTP verb (get/post/put/delete).</li>
 *   <li>{@code then()}   -> describe what to check / extract the response.</li>
 * </ul>
 */
public final class ApiClient {

    private ApiClient() {
    }

    /**
     * Builds a request pre-configured with the API base URI and JSON headers.
     * {@code log().all()} prints the full request so you can see what was sent.
     */
    public static RequestSpecification baseRequest() {
        return RestAssured.given()
                .baseUri(ConfigReader.get("api.base.uri"))
                .header("Accept", "application/json")
                .log().all();
    }

    /** Send a GET request and return the response. */
    public static Response get(String path) {
        return baseRequest()
                .when()
                .get(path)
                .then()
                .log().all()          // print the response
                .extract().response(); // give us the Response object to assert on
    }

    /** Send a POST request with a JSON body and return the response. */
    public static Response post(String path, Object body) {
        return baseRequest()
                .body(body)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract().response();
    }
}
