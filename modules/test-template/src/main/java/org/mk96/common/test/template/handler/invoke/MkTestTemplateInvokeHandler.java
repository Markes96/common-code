package org.mk96.common.test.template.handler.invoke;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.mk96.common.test.AbstractMkTest;
import org.mk96.common.test.template.AbstractMkTestTemplate;
import org.mk96.common.test.template.exception.MkTestTemplateException;
import org.mk96.common.test.template.type.MkTestTemplateExecution;
import org.mk96.common.util.MkLoadFileUtils;
import org.mk96.common.util.MkReflectionUtils;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkTestTemplateInvokeHandler {

	public Object invoke(final AbstractMkTestTemplate testTemplate,
			final MkTestTemplateExecution execution)
			throws IOException, IllegalAccessException, InvocationTargetException {

		final Object[] args = buildArgs(execution);
		return MkReflectionUtils.invokeMethod(testTemplate, execution.getTest(), args);
	}

    private static Object[] buildArgs(final MkTestTemplateExecution execution) throws IOException {
        return switch (execution.getTestType()) {
            case LOAD_EXAMPLE -> loadExamples(execution);
            case LOAD_PATH -> AbstractMkTest.toArray(execution.getPath());
            case DEFAULT -> new Object[0];
        };
    }

	private Object[] loadExamples(final MkTestTemplateExecution execution) throws IOException {

		final String[] exampleNames = execution.getExampleNames();
		final Class<?>[] testParameters = execution.getTest().getParameterTypes();

		if (testParameters.length != exampleNames.length) {
            throw new MkTestTemplateException(
					"Number of test arguments must be equal than number of ExampleNames");
        }

		final List<Object> files = new ArrayList<>();
		for (int i = 0; i < testParameters.length; i++) {
			final Path examplePath = Paths.get(execution.getPath(), exampleNames[i]);
			final Object example = MkLoadFileUtils.load(testParameters[i], examplePath);
			files.add(example);
		}

		return files.toArray();
	}

}
