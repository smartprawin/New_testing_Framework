package com.qa.automation.stepdefinitions;

import com.microsoft.playwright.Page;
import com.qa.automation.utils.ScenarioContext;

/**
 * BASE STEP-DEFINITION CLASS.
 *
 * <p>Every step-definition class can extend this so they all share the
 * {@link Page} field and a small helper to grab it.
 *
 * <p>IMPORTANT Cucumber rule: a step-definition class may NOT extend a class
 * that itself declares hooks ({@code @Before}/@After}) or step definitions.
 * So this base class deliberately does NOT have any Cucumber annotations.
 * Instead it exposes the {@link #capturePage()} helper, and each subclass
 * calls it from its OWN {@code @Before} method.
 */
public class BaseStepDefinition {

    /** The Playwright page (tab) for the current scenario. */
    protected Page page;

    /**
     * Grab the page that Hooks prepared for this scenario (stored in
     * ScenarioContext) and keep it in {@link #page}.
     * Call this from a {@code @Before} method in the subclass.
     */
    protected void capturePage() {
        this.page = ScenarioContext.getPage();
    }
}
