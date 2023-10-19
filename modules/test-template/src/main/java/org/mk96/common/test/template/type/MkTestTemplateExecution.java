package org.mk96.common.test.template.type;

import java.lang.reflect.Method;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.test.template.constant.MkTestTemplateConstant;
import org.mk96.common.type.MkObject;
import lombok.Getter;

@Getter
public class MkTestTemplateExecution extends MkObject {

    private final MkTestTemplateParameter parameter;

    private MkTestTemplateType testType = MkTestTemplateType.DEFAULT;
    private MkTestTemplateMode testMode = MkTestTemplateMode.DEFAULT;

    private String[] exampleNames = {MkTestTemplateConstant.DEFAULT_EXAMPLE_NAME};
    private String[] resultNames = {MkTestTemplateConstant.DEFAULT_RESULT_NAME};
    private String[] regexForReplace = {};

    public MkTestTemplateExecution(final MkTestTemplateParameter parameter,
            final MkTestTemplateConfiguration configuration) {

        this.parameter = parameter;

        if (Objects.nonNull(configuration)) {
            setTestType(configuration.type());
            setTestMode(configuration.mode());
            setExampleNames(configuration.exampleName());
            setResultNames(configuration.resultName());
            setRegexForReplace(configuration.regexForReplace());
        }

        final MkTestTemplate subConfiguration =
                this.parameter.getTest().getAnnotation(MkTestTemplate.class);

        if (Objects.nonNull(subConfiguration)) {
            setTestType(subConfiguration.type());
            setTestMode(subConfiguration.mode());
            setExampleNames(subConfiguration.exampleName());
            setResultNames(subConfiguration.resultName());
            setRegexForReplace(subConfiguration.regexForReplace());
        }

    }

    public String getPath() {
        return parameter.getPath();
    }

    public Method getTest() {
        return parameter.getTest();
    }

    private void setTestType(final MkTestTemplateType testType) {
        if (Objects.nonNull(testType) && testType != MkTestTemplateType.DEFAULT) {
            this.testType = testType;
        }

    }

    private void setTestMode(final MkTestTemplateMode testMode) {
        if (Objects.nonNull(testMode) && testMode != MkTestTemplateMode.DEFAULT) {
            this.testMode = testMode;
        }

    }

    private void setExampleNames(final String[] exampleNames) {
        if (ArrayUtils.isNotEmpty(exampleNames)) {
            this.exampleNames = exampleNames;
        }

    }

    private void setResultNames(final String[] resultNames) {
        if (ArrayUtils.isNotEmpty(resultNames)) {
            this.resultNames = resultNames;
        }

    }

    private void setRegexForReplace(final String[] regexForReplace) {
        if (ArrayUtils.isNotEmpty(regexForReplace)) {
            this.regexForReplace = regexForReplace;
        }

    }

}
