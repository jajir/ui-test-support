package com.cubiculus.testsupport;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.io.File;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public abstract class WebDriverExtension
        implements BeforeAllCallback, AfterAllCallback, ExtensionContext.Store.CloseableResource {

    /**
     * Chrome drive location system property.
     */
    private final static String CHROMEDRIVER_FILE_SYSTEM_PROPERTY_NAME = "chromedriver";

    /**
     * Default value for chrome driver location.
     */
    private final static String CHROMEDRIVER_FILE_DEFAULT_VALUE = "/bin/chromedriver";

    public static final String WEB_DRIVER = "webDriver";

    private static boolean started = false;

    private static WebDriverManager webdriverManager;

    private File getChromedriverExecutableFile() {
        String out = getChromeDriverExecutablePath();
        if (out == null) {
            final String str = System.getProperty(CHROMEDRIVER_FILE_SYSTEM_PROPERTY_NAME);
            return str == null ? new File(CHROMEDRIVER_FILE_DEFAULT_VALUE) : new File(str.trim());
        } else {
            return new File(out);
        }
    }

    /**
     * This is default implementation. When Chrome executable are not in path than
     * you should overwrite it.
     * 
     * @return
     */
    protected String getChromeDriverExecutablePath() {
        return null;
    }

    @Override
    public void beforeAll(final ExtensionContext context) {
        if (!started) {
            started = true;
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is
            // shut down
            webdriverManager = new WebDriverManager(getChromedriverExecutableFile());

            /*
             * This is trick, it register this class class as closable global resource. When
             * all test are done than close() at this class is called.
             */
            context.getRoot().getStore(GLOBAL).put("some unique key", this);

            context.getRoot().getStore(GLOBAL).put(WEB_DRIVER, webdriverManager.getDriver());
        }
    }

    @Override
    public void close() {
        // Your "after all tests" logic goes here
        if (started) {
            webdriverManager.close();
        }
    }

    @Override
    public void afterAll(final ExtensionContext context) throws Exception {
        if (started) {
//	    webdriverManager.close();
        }
    }
}
