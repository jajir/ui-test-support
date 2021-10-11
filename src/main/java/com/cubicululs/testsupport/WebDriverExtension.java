package com.cubicululs.testsupport;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WebDriverExtension
	implements BeforeAllCallback, AfterAllCallback, ExtensionContext.Store.CloseableResource {

    public static final String WEB_DRIVER = "webDriver";

    private static boolean started = false;

    private static WebDriverManager webdriverManager;

    @Override
    public void beforeAll(final ExtensionContext context) {
	if (!started) {
	    started = true;
	    // Your "before all tests" startup logic goes here
	    // The following line registers a callback hook when the root test context is
	    // shut down
	    webdriverManager = new WebDriverManager();

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
