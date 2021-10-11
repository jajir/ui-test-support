package com.cubicululs.testsupport;

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

    private final static String RANDOM_TEXT_PIECE = "0123456789";

    /**
     * Umoznuje oznacit testy, ktere meni data.
     */
    protected final static String CHANGE_DATA = "changeData";

    /**
     * Umoznuje oznacit testy, ktere nemeni data. Takove to testy pak lze poustet na
     * produkci.
     */
    protected final static String PRESERVE_DATA = "preserveData";

    /**
     * Umoznuje oznacit testy, ktere chceme automaticky spoustet na CI a pri buildu
     * vetve. Vzdy budou testy, ktere jsou nestabilni, nebo vy vyvoji, nebo vypnute,
     * ktere nebudeme chtit provadet.
     */
    protected final static String SAFE_TEST = "safeTest";

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

    /**
     * Method generate random text of some length. It's useful for testing too long
     * strings.
     * 
     * @param length
     * @return
     */
    protected String getRandomText(final int length) {
        final StringBuilder stringBuilder = new StringBuilder(5000);
        for (int len = 0; len < length; len += RANDOM_TEXT_PIECE.length()) {
            stringBuilder.append(RANDOM_TEXT_PIECE);
        }
        return stringBuilder.toString();
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
