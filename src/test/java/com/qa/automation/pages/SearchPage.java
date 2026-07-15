package com.qa.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for a search screen (we use Wikipedia as a free, stable example).
 *
 * <p>This class is here to show you how to ADD a new page object:
 * 1. Create a class that {@code extends BasePage}.
 * 2. Declare your locators once in the constructor.
 * 3. Expose friendly methods (searchFor, getFirstHeading) for the step defs.
 *
 * <p>Everything else (openUrl, type, click, getText...) is inherited from
 * {@link BasePage}, so each new page object stays small.
 */
public class SearchPage extends BasePage {

    // The search box at the top of Wikipedia.
    private final Locator searchInput;
    // The big heading at the top of a Wikipedia article/page.
    private final Locator firstHeading;

    public SearchPage(Page page) {
        super(page);
        this.searchInput = page.locator("input[name='search']");
        this.firstHeading = page.locator("#firstHeading");
    }

    /**
     * Type a term into the search box and submit it (press Enter).
     * (Selenium equivalent: element.sendKeys(text + Keys.ENTER))
     */
    public void searchFor(String term) {
        type(searchInput, term);
        searchInput.press("Enter"); // Playwright can press keys on a locator
    }

    /** Read the page heading text (e.g. the article title). */
    public String getFirstHeading() {
        return getText(firstHeading);
    }
}
