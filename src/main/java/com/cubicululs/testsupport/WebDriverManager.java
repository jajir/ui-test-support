package com.cubicululs.testsupport;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manage webdriver. Webdriver allows to control chromium. Initialize it and
 * close it.
 *
 * @author jan
 *
 */
class WebDriverManager {

    /**
     * Name of system property in which could defined if test should run in headless
     * mode.
     */
    private final static String HEADLESS_SYSTEM_PROPERTY_NAME = "headless";

    /**
     * When no headless mode is specified that this value is used.
     */
    private final static Boolean HEADLESS_DEFAULT_VALUE = false;

    /**
     * Chrome drive location system property.
     */
    private final static String CHROMEDRIVER_FILE_SYSTEM_PROPERTY_NAME = "chromedriver";

    /**
     * Default value for chrome driver location.
     */
    private final static String CHROMEDRIVER_FILE_DEFAULT_VALUE = "/bin/chromedriver";

    /**
     * System property in which is defined window size.
     */
    private final static String WINDOWS_SIZE_PROPERTY_NAME = "windowSize";

    /**
     * Window size default value.
     */
    private final static String WINDOWS_SIZE_DEFAULT_VALUE = "1200,800";

    private static Logger logger = LoggerFactory.getLogger(WebDriverManager.class);

    private final ChromeDriverService service;

    private final WebDriver driver;
    private final static String CHROMIUM_WEBDRIVER_LOG_FILE = "target/chromedriver.log";
    
 
    public WebDriverManager() {
        logger.debug("Starting web driwer");
        logger.debug("Chrome driver file is at '{}'", getChromedriverExecutable());
        logger.debug("Headless mode is enabled '{}'", isHeadless());
        logger.debug("Window size is '{}'", getWindowsSize());

        // Set webdriver logging if it wasn't alreeady set
        if (System.getProperty("webdriver.chrome.logfile") == null) {
            final File file = new File(CHROMIUM_WEBDRIVER_LOG_FILE);
            System.setProperty("webdriver.chrome.logfile", file.getAbsolutePath());
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            logger.debug("Chrome webdriver verbose logging was set to '{}'",
                    file.getAbsolutePath());
        } else {
            logger.debug("Chrome webdriver logging was already set to '{}'",
                    System.getProperty("webdriver.chrome.logfile"));
        }

        try {
            service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(getChromedriverExecutable()).usingAnyFreePort().build();
            service.start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        
        
        final HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", AbstractBaseTest.DOWNLOAD_FILE_PATH);
        
        final ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.setHeadless(isHeadless());
        options.addArguments("--window-size=" + getWindowsSize());
        options.addArguments("--enable-logging --v=1");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

//        options.setLogLevel(ChromeDriverLogLevel.ALL);
        driver = new RemoteWebDriver(service.getUrl(), options);

        /*
         * How long wait until page is loaded.
         *
         * NOTE: I don't know why, but it it block chrome to load any page.
         */
//        getDriver().manage().timeouts().pageLoadTimeout(1_000, TimeUnit.MILLISECONDS);

        /**
         * How long wait until element is reported as not found.
         */
        getDriver().manage().timeouts().implicitlyWait(1_000, TimeUnit.MILLISECONDS);
    }

    /**
     * Provide actual information when to enable headless mode.
     */
    private boolean isHeadless() {
        final String str = System.getProperty(HEADLESS_SYSTEM_PROPERTY_NAME,
                HEADLESS_DEFAULT_VALUE.toString());
        return str == null ? HEADLESS_DEFAULT_VALUE : Boolean.valueOf(str);
    }

    private File getChromedriverExecutable() {
        final String str = System.getProperty(CHROMEDRIVER_FILE_SYSTEM_PROPERTY_NAME);
        return str == null ? new File(CHROMEDRIVER_FILE_DEFAULT_VALUE) : new File(str.trim());
    }

    private String getWindowsSize() {
        final String str = System.getProperty(WINDOWS_SIZE_PROPERTY_NAME);
        return str == null ? WINDOWS_SIZE_DEFAULT_VALUE : str;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        logger.debug("Closing web driwer");
        service.stop();
    }
}
