package com.cubiculus.testsupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.google.common.base.Preconditions;

/**
 * Basic test integrating selenium web driver and junit.
 * 
 * @author jan
 *
 */
public abstract class AbstractWebdriverTest {

    private WebDriver driver;

    public AbstractWebdriverTest() {
        /*
         * Load class. It block following exception:
         * 
         * 
         * java.lang.NoClassDefFoundError: Could not initialize class
         * org.openqa.selenium.WebDriverException
         */
        new WebDriverException("No used exception");
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void verifyThatTitleIs(final String expectedTitle) {
        final String title = getDriver().getTitle();
        assertEquals(expectedTitle, title,
                String.format("Expected title was '%s' but there is '%s'", expectedTitle, title));
    }

    protected void verifyThatTitleContans(final String expectedTitle) {
        final String title = getDriver().getTitle();
        assertTrue(title.contains(expectedTitle), String.format(
                "Given title '%s' should be in real page title '%s'", expectedTitle, title));
    }

    protected void sleep(final long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    protected void beforeEach(final WebDriver webDriver) {
        driver = webDriver;
        Preconditions.checkNotNull(driver, "WebDriver is null.");
    }

    @AfterEach
    protected void afterEach() {
        driver.manage().deleteAllCookies();
        driver = null;
    }

}
