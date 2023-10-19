package org.mk96.common.test.template.handler.compare;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.mk96.common.test.AbstractMkTest;
import org.mk96.common.test.template.exception.MkTestTemplateException;
import org.mk96.common.test.template.type.MkTestTemplateExecution;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkTestTemplateCompareHandler {

	public void compare(final MkTestTemplateExecution execution, final Object[] results)
			throws JsonProcessingException {

		final String[] resultNames = execution.getResultNames();

		if (results.length != resultNames.length) {
            throw new MkTestTemplateException("Number of test results must be equal than number of ResultNames");
        }

		for (int i = 0; i < results.length; i++) {
			final Object result = results[i];
			final Path resultPath = Paths.get(execution.getPath(), resultNames[i]);
			AbstractMkTest.compareResultToFile(result, resultPath);
		}

	}

}
