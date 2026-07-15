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
    public void openUrl(String url) {
        page.navigate(url);
    }

    /**
     * Type text into a field. (Selenium: element.sendKeys(text))
     * Playwright auto-waits for the field to be ready before filling, so no
     * extra wait is needed (and adding one can fight with slowMo).
     */
    public void type(Locator locator, String text) {
        locator.fill(text);
    }

    /**
     * Click an element. (Selenium: element.click())
     * Playwright waits for the element to be visible, stable and enabled first.
     */
    public void click(Locator locator) {
        locator.click();
    }

    /** Read the visible text of an element. (Selenium: element.getText()) */
    public String getText(Locator locator) {
        return locator.textContent().trim();
    }

    /** Is the element visible? (Selenium: element.isDisplayed()) */
    public boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    /** Current page URL. (Selenium: driver.getCurrentUrl()) */
    public String getCurrentUrl() {
        return page.url();
    }

    /** Take a screenshot and return the image bytes. */
    public byte[] takeScreenshot() {
        return page.screenshot();
    }
}
