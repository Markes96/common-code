package org.mk96.common.test.template;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mk96.common.test.AbstractMkTest;
import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.test.template.constant.MkTestTemplateConstant;
import org.mk96.common.test.template.context.MkTestTemplateInvocationContextProvider;
import org.mk96.common.test.template.handler.compare.MkTestTemplateCompareHandler;
import org.mk96.common.test.template.handler.filter.MkTestTemplateFilterHandler;
import org.mk96.common.test.template.handler.initialize.example.MkTestTemplateExampleFileInitalizeHandler;
import org.mk96.common.test.template.handler.initialize.result.MkTestTemplateResultFileInitalizeHandler;
import org.mk96.common.test.template.handler.invoke.MkTestTemplateInvokeHandler;
import org.mk96.common.test.template.handler.update.MkTestTemplateResultFileUpdateHandler;
import org.mk96.common.test.template.type.MkTestTemplateExecution;
import org.mk96.common.test.template.type.MkTestTemplateMode;
import org.mk96.common.test.template.type.MkTestTemplateParameter;
import org.mk96.common.test.template.type.MkTestTemplateType;
import org.mk96.common.util.MkSerializeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Abstract class used to automatically generate a test run for each folder found within a given
 * path.
 * </p>
 *
 * <ul>
 * <li>This folder can contain files.</li>
 * <li>To indicate the main folder of the tests, use {@link MkTestTemplateConfiguration} annotation
 * at the class level.</li>
 * <li>To indicate the methods that are going to be used as a test, use the annotations
 * {@link MkTestTemplateConfiguration} at the class level, and {@link MkTestTemplate} at the method
 * level, always predominating the annotation at the method level</li>
 * <li>If you want to load an input file, so that it is injected by parameter, the loadFile
 * parameter will be used, which is configurable from anotations {@link MkTestTemplateConfiguration}
 * at the class level, and {@link MkTestTemplate} at the method level, always predominating the
 * annotation at the method level, exampleName is configurable too. The test automatically detects
 * if the parameter is {@link String} or {@link Byte} array and loads the file in one of the two
 * formats</li>
 * <li>In case the test function returns a value other than void, it will be automatically compared
 * with the result file. The test will automatically detect if return type is {@link String},
 * {@link Byte} array or an {@linkplain Object} and will load the file in that same format. (Note:
 * if return type is an {@linkplain Object} it will be serialized with default pretty printer in
 * String format), result name is configuable from anotations {@link MkTestTemplateConfiguration} at
 * the class level, and {@link MkTestTemplate} at the method level, always predominating the
 * annotation at the method level</li>
 * </ul>
 *
 * @see MkTestTemplate
 * @see MkTestTemplateConfiguration
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractMkTestTemplate extends AbstractMkTest {

    private MkTestTemplateConfiguration configuration;

    @BeforeAll
    void configTemplate() {

        MkTestTemplateInvocationContextProvider.clean();

        // Config source path
        configuration = this.getClass().getAnnotation(MkTestTemplateConfiguration.class);

        final MkTestTemplateMode testMode =
                Objects.nonNull(configuration) ? configuration.mode() : MkTestTemplateMode.DEFAULT;

        final String templatePath = Objects.nonNull(configuration) ? configuration.path()
                : MkTestTemplateConstant.DEFAULT_PATH;

        final List<Method> testMethods = Stream.of(this.getClass().getDeclaredMethods())
                .filter(test -> test.isAnnotationPresent(MkTestTemplate.class))
                .sorted(MkTestTemplateConstant.TEST_COMPARATOR).toList();

        MkTestTemplateInvocationContextProvider.setTestMode(testMode);
        MkTestTemplateInvocationContextProvider.setTemplatePath(templatePath);
        MkTestTemplateInvocationContextProvider.setTestMethods(testMethods);
    }

    @AfterAll
    void resetTemplate() {
        MkTestTemplateInvocationContextProvider.clean();
    }

    @TestTemplate
    @ExtendWith(MkTestTemplateInvocationContextProvider.class)
    void mkTestTemplateExecutor(final MkTestTemplateParameter parameter)
            throws IOException, IllegalAccessException, InvocationTargetException {

        final MkTestTemplateExecution execution =
                new MkTestTemplateExecution(parameter, configuration);

        switch (execution.getTestMode()) {
            case INIT -> initTest(execution);
            case UPDATE -> updateTest(execution);
            case DEFAULT, TEST -> executeTest(execution);
        }
    }

    private void initTest(final MkTestTemplateExecution execution) {
        if (execution.getTestType() == MkTestTemplateType.LOAD_EXAMPLE) {
            MkTestTemplateExampleFileInitalizeHandler.init(execution);
        }
        if (execution.getTest().getReturnType() != void.class) {
            MkTestTemplateResultFileInitalizeHandler.init(execution);
        }
    }

    private void updateTest(final MkTestTemplateExecution execution)
            throws IllegalAccessException, InvocationTargetException, IOException {
        if (execution.getTest().getReturnType() != void.class) {
            MkTestTemplateResultFileInitalizeHandler.init(execution);
            final Object testResult = MkTestTemplateInvokeHandler.invoke(this, execution);
            final Object[] testResultArray = MkTestTemplateFilterHandler
                    .filter(convertToArray(testResult), execution.getRegexForReplace());
            MkTestTemplateResultFileUpdateHandler.updateResults(execution, testResultArray);
        }
    }

    private void executeTest(final MkTestTemplateExecution execution)
            throws IOException, IllegalAccessException, InvocationTargetException {
        final Object testResult = MkTestTemplateInvokeHandler.invoke(this, execution);
        if (execution.getTest().getReturnType() != void.class) {
            final Object[] testResultArray = MkTestTemplateFilterHandler
                    .filter(convertToArray(testResult), execution.getRegexForReplace());
            MkTestTemplateCompareHandler.compare(execution, testResultArray);
        }
    }

    private Object[] convertToArray(final Object obj) {
        return obj instanceof Object[] ? (Object[]) obj : super.toArray(obj);
    }

    protected Object filterTestResult(final Object testResult, final String[] regex)
            throws JsonProcessingException {
        final Object result =
                testResult instanceof byte[] || testResult instanceof String ? testResult
                        : MkSerializeUtils.writeAsString(testResult);
        return super.filterResult(result, regex);
    }

}
