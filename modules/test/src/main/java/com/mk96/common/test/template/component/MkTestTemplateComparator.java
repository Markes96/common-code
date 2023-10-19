package com.mk96.common.test.template.component;

import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mk96.common.test.AbstractMkTest;
import com.mk96.common.test.template.exception.MkTestTemplateException;
import com.mk96.common.test.template.type.MkTestTemplateExecution;

public class MkTestTemplateComparator {

  public void compare(final MkTestTemplateExecution execution, final Object[] results)
      throws JsonProcessingException {

    final String[] resultNames = execution.getResultNames();

    if (results.length != resultNames.length)
      throw new MkTestTemplateException(
          "Number of test results must be equal than number of ResultNames");

    for (int i = 0; i < results.length; i++) {
      final Object result = results[i];
      final Path resultPath = Paths.get(execution.getPath(), resultNames[i]);
      AbstractMkTest.compareResultToFile(result, resultPath);
    }

  }

}
