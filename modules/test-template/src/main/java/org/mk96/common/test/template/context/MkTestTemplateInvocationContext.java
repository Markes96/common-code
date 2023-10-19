package org.mk96.common.test.template.context;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.mk96.common.test.template.type.MkTestTemplateParameter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MkTestTemplateInvocationContext implements TestTemplateInvocationContext {

	private final String sourcePath;
	private final String testPath;
	private final Method testMethod;

	@Override
	public String getDisplayName(final int invocationIndex) {

		final String slashPath = testPath.replace("\\", "/");
		final String displayPath = sourcePath + StringUtils.substringAfterLast(slashPath, sourcePath);
		return "Test: [Path -> '.../" + displayPath + "'] :: [Test -> '" + testMethod.getName()
				+ "']";
	}

	@Override
	public List<Extension> getAdditionalExtensions() {

		final MkTestTemplateParameter parameter = new MkTestTemplateParameter(testPath, testMethod);
		final MkTestTemplateParameterResolver parameterResolver = new MkTestTemplateParameterResolver(parameter);

		return List.of(parameterResolver);
	}
}
