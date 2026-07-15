package com.qa.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * BASE PAGE CLASS  (the Playwright equivalent of your old Selenium base page).
 *
 * <p>In Selenium you typically had a base class holding the {@code WebDriver}
 * and a few helper methods (click, type, wait...). Here the equivalent is the
 * Playwright {@link Page} (a browser tab). Every page object in the framework
 * will {@code extend BasePage} and reuse these helpers.
 *
 * <p>---------------------------------------------------------------
 *  Selenium concept            ->  Playwright equivalent
 * ---------------------------------------------------------------
 *  WebDriver                   ->  Page
 *  driver.get(url)             ->  page.navigate(url)
 *  element.sendKeys(text)      ->  locator.fill(text)
 *  element.click()             ->  locator.click()
 *  element.getText()           ->  locator.textContent()
 *  element.isDisplayed()       ->  locator.isVisible()
 *  WebDriverWait + ExpectedConditions ->  NOT NEEDED (Playwright auto-waits)
 *  ((TakesScreenshot)driver)   ->  page.screenshot()
 * ---------------------------------------------------------------
 *
 * <p>The biggest mindset change: Playwright locators AUTO-WAIT and retry until
 * the element is ready, so you rarely write explicit waits.
 */
public abstract class BasePage {

    // The page (tab) we interact with. "protected" so subclasses can use it.
    protected final Page page;

    /** Subclasses pass the page they received from the step definition. */
    protected BasePage(Page page) {
        this.page = page;
    }

    /** Open a URL. (Selenium: driver.get(url)) */
    protected void openUrl(String url) {
        page.navigate(url);
    }

    /**
     * Type text into a field. (Selenium: element.sendKeys(text))
     * Playwright waits for the field to be editable before typing.
     */
    protected void type(Locator locator, String text) {
        locator.fill(text);
    }

    /**
     * Click an element. (Selenium: element.click())
     * Playwright waits for the element to be visible, stable and enabled first.
     */
    protected void click(Locator locator) {
        locator.click();
    }

    /** Read the visible text of an element. (Selenium: element.getText()) */
    protected String getText(Locator locator) {
        return locator.textContent();
    }

    /** Is the element visible? (Selenium: element.isDisplayed()) */
    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    /** Current page URL. (Selenium: driver.getCurrentUrl()) */
    protected String getCurrentUrl() {
        return page.url();
    }

    /** Take a screenshot and return the image bytes. */
    protected byte[] takeScreenshot() {
        return page.screenshot();
    }
}
