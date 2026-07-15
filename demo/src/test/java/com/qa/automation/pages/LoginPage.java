package com.qa.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for the login screen.
 *
 * <p>A "Page Object" wraps one screen/page of the app. It exposes friendly
 * methods (goToLogin, login, ...) and hides the messy selectors. Step
 * definitions talk to this class instead of touching Playwright directly.
 *
 * <p>Extends {@link BasePage} to inherit the common helpers (openUrl, type...).
 */
public class LoginPage extends BasePage {

    // Locators are found once here and reused (like @FindBy in Selenium).
    // A Locator is Playwright's way of saying "where to find an element".
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {
        super(page);
        // page.locator(selector) is the Playwright version of
        // driver.findElement(By.cssSelector("...")) in Selenium.
        // The-internet demo login form field ids:
        this.usernameInput = page.locator("#username");
        this.passwordInput = page.locator("#password");
        this.loginButton = page.locator("button[type='submit']");
    }

    /** Navigate to the login page using the configured base URL. */
    public void goToLogin(String baseUrl) {
        openUrl(baseUrl + "/login");
    }

    /** Fill in the username and password fields. */
    public void enterCredentials(String username, String password) {
        type(usernameInput, username);   // inherited from BasePage
        type(passwordInput, password);   // inherited from BasePage
    }

    /** Click the submit/login button. */
    public void submit() {
        click(loginButton);              // inherited from BasePage
    }
}
