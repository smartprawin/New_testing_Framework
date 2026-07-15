package com.qa.automation.utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

/**
 * Shares the current Playwright {@link Page} (and its {@link BrowserContext})
 * with the step-definition classes during a scenario.
 *
 * <p>Why do we need this? Cucumber creates a fresh instance of each step
 * definition class per scenario, and the browser/page is created in a
 * separate "Hooks" class. We use this little helper so step definitions can
 * grab the page that Hooks prepared for them.
 *
 * <p>{@code ThreadLocal} keeps one value per thread, which makes the framework
 * safe if you ever run scenarios in parallel.
 */
public final class ScenarioContext {

    // One Page per thread.
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();
    // One BrowserContext per thread.
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();

    private ScenarioContext() {
    }

    /**
     * Called by Hooks at the start of a scenario. Creates a new browser
     * context (like a private/incognito window) and opens a page inside it.
     */
    public static void setContext(BrowserContext context) {
        CONTEXT.set(context);
        PAGE.set(context.newPage());
    }

    /** The page for the current scenario (a browser tab). */
    public static Page getPage() {
        return PAGE.get();
    }

    /** The browser context for the current scenario. */
    public static BrowserContext getContext() {
        return CONTEXT.get();
    }

    /** Clears the stored values so nothing leaks between scenarios. */
    public static void clear() {
        PAGE.remove();
        CONTEXT.remove();
    }
}
