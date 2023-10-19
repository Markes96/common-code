package org.mk96.common.test.template.handler.update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.mk96.common.test.template.exception.MkTestTemplateException;
import org.mk96.common.test.template.type.MkTestTemplateExecution;
import org.mk96.common.util.MkSerializeUtils;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkTestTemplateResultFileUpdateHandler {

    public void updateResults(final MkTestTemplateExecution execution, Object[] results) {

        final String[] resultNames = execution.getResultNames();

        if (resultNames.length != results.length) {
            throw new MkTestTemplateException(
                    "Number of test results must be equal than number of ResultNames");
        }

        for (int i = 0; i < resultNames.length; i++) {
            String resultName = resultNames[i];
            Object result = results[i];
            updateResult(execution.getPath(), resultName, result);
        }

    }

    private void updateResult(String basePath, String resultName, Object result) {
        Path examplePath = Paths.get(basePath, resultName);

        byte[] writeableResult;
        if(result instanceof byte[] resultByte) {
            writeableResult = resultByte;
        } else if(result instanceof String resultString) {
            writeableResult = resultString.getBytes();
        } else {
            writeableResult = MkSerializeUtils.writeAsBytes(result);
        }

        try {
            if (Files.exists(examplePath)) {
                Files.write(examplePath, writeableResult);
            }
        } catch (IOException e) {
            throw new MkTestTemplateException(
                    "Error creating updating result: " + resultName + " ", e);
        }
    }

}
