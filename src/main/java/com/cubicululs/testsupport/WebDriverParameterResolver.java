package com.cubicululs.testsupport;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Preconditions;

public class WebDriverParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return parameterContext.getParameter().getType() == WebDriver.class;
	}

	@Override
	public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
			throws ParameterResolutionException {
		final WebDriver webDriver = extensionContext.getRoot().getStore(GLOBAL).get(WebDriverExtension.WEB_DRIVER,
				WebDriver.class);
		Preconditions.checkNotNull(webDriver);
		return webDriver;
	}

}
