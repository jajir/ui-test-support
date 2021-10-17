package com.cubiculus.testsupport;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * When test fails it take screenshot before closing browser. Screenshots will
 * be in 'target' directory.
 *
 * @author jan
 *
 */
public class TakeScreenshotTestWatcher implements TestWatcher {

    private final static String IMAGE_EXTENSION = "png";

    private static Logger logger = LoggerFactory.getLogger(TakeScreenshotTestWatcher.class);

    @Override
    public void testFailed(final ExtensionContext context, final Throwable cause) {
        logger.debug("Starting taking screenshot");
        final File destFile = new File(
                "target" + File.separator + getImageName(getTestMethod(context)));
        logger.debug("Image will be stored at {}", destFile.getAbsoluteFile().toString());

        final WebDriver driver = getWebDriver(context);
        final TakesScreenshot scrShot = (TakesScreenshot) driver;
        final File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

        try {
            // Copy file at destination
            Files.copy(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTestMethod(final ExtensionContext context) {
        final String testClass = context.getTestClass().map(Class::getName)
                .orElse("unknown_test_class");
        return testClass + "_" + context.getDisplayName();
    }

    private WebDriver getWebDriver(final ExtensionContext context) {
        return context.getRoot().getStore(GLOBAL).get(WebDriverExtension.WEB_DRIVER,
                WebDriver.class);
    }

    private String getImageName(final String testDisplayName) {
        Preconditions.checkNotNull(testDisplayName);
        String ret;
        if (testDisplayName.endsWith("()")) {
            ret = testDisplayName.substring(0, testDisplayName.length() - 2);
        } else {
            ret = testDisplayName;
        }
        // u parametrizovaných testů obsahoval název příliš mnoho dat a uložení padlo
        // na IOException (Too long name)
        if (ret.length() > 120) {
            ret = ret.substring(0, 120);
        }

        ret += "." + IMAGE_EXTENSION;
        return ret;
    }

}
