package com.qa.automation.stepdefinitions;

import com.microsoft.playwright.Page;
import com.qa.automation.utils.ScenarioContext;
import io.cucumber.java.Before;

/**
 * BASE STEP-DEFINITION CLASS.
 *
 * <p>Every step-definition class can extend this so they all automatically
 * receive the Playwright {@link Page} that Hooks prepared for the scenario.
 *
 * <p>Why a base class? Without it, each step class would have to repeat the
 * "grab the page from ScenarioContext" logic. Here it happens once.
 *
 * <p>Order note: Hooks (order 1) creates the page BEFORE this hook (order 2)
 * runs, so {@code page} is guaranteed to be ready.
 *
 * <p>Note: this class is intentionally NOT abstract. Cucumber needs to create
 * an instance of it to run the {@code @Before} hook, and it cannot instantiate
 * an abstract class.
 */
public class BaseStepDefinition {

    /** The Playwright page (tab) for the current scenario. */
    protected Page page;

    @Before(order = 2)
    public void capturePage() {
        this.page = ScenarioContext.getPage();
    }
}
