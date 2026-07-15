package com.qa.automation.hooks;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.automation.utils.ConfigReader;
import com.qa.automation.utils.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Hooks run automatically around your scenarios (like {@code @BeforeMethod} /
 * {@code @AfterMethod} in TestNG, or JUnit's {@code @BeforeEach}).
 *
 * <p>Playwright object model (the big difference from Selenium):
 * <pre>
 *   Playwright  -> the entry point / factory (like a driver manager)
 *     Browser   -> the actual browser process (Chromium, Firefox, WebKit)
 *       BrowserContext -> an isolated session (like an incognito window)
 *         Page  -> a single tab you interact with (this is what we test with)
 * </pre>
 *
 * <p>In Selenium you usually had ONE driver for the whole run. Here we create
 * a fresh BrowserContext + Page for every scenario so tests don't share
 * cookies/login state (cleaner, more reliable).
 */
public class Hooks {

    // Static because the browser is shared across all scenarios in the run.
    private static Playwright playwright;
    private static Browser browser;

    /**
     * STATIC @Before = runs ONCE, before the very first scenario.
     * (In Cucumber, a static hook acts as a global "before all".)
     * This is where we start the browser.
     */
    @Before(order = 0)
    public static void launchBrowser() {
        if (playwright == null) {
            // 1. Start the Playwright factory.
            playwright = Playwright.create();

            // 2. Read settings from config.properties.
            boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "true"));
            String name = ConfigReader.get("browser", "chromium").toLowerCase();

            // 3. Launch the chosen browser (headless = no visible window).
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
            browser = switch (name) {
                case "firefox" -> playwright.firefox().launch(options);
                case "webkit"   -> playwright.webkit().launch(options);
                default         -> playwright.chromium().launch(options);
            };
        }
    }

    /**
     * INSTANCE @Before = runs BEFORE EACH scenario.
     * Creates a clean context + page and stores it for the step definitions.
     */
    @Before(order = 1)
    public void createContext(Scenario scenario) {
        ScenarioContext.setContext(browser.newContext());
    }

    /**
     * INSTANCE @After = runs AFTER EACH scenario.
     * If the scenario failed, attach a screenshot to the report.
     * Then close the context so the next scenario starts fresh.
     */
    @After(order = 1)
    public void closeContext(Scenario scenario) {
        if (scenario.isFailed()) {
            Page page = ScenarioContext.getPage();
            if (page != null) {
                // page.screenshot() returns the image bytes.
                byte[] screenshot = page.screenshot();
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
        }
        if (ScenarioContext.getContext() != null) {
            ScenarioContext.getContext().close();
        }
        ScenarioContext.clear();
    }

    /**
     * STATIC @After = runs ONCE, after the last scenario.
     * Closes the browser and the Playwright factory (frees resources).
     */
    @After(order = 0)
    public static void closeBrowser() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
