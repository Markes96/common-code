package org.mk96.common.test.template.context;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.mk96.common.test.template.exception.MkTestTemplateException;
import org.mk96.common.test.template.type.MkTestTemplateMode;
import org.mk96.common.type.MkException;
import org.mk96.common.util.MkLoadFileUtils;

public class MkTestTemplateInvocationContextProvider
        implements TestTemplateInvocationContextProvider {

    private static final String BASE_ERROR_MESSAGE = "Error loading invocation context: ";
    private static final String BASE_RESOURCE_PATH = "src/test/resources/";

    // =======================================================
    // Parametrization
    // =======================================================

    private static final ThreadLocal<MkTestTemplateMode> TEST_MODE;
    private static final ThreadLocal<String> TEMPLATE_PATH;
    private static final ThreadLocal<List<Method>> TEST_METHODS;

    static {
        TEST_MODE = ThreadLocal.withInitial(() -> MkTestTemplateMode.DEFAULT);
        TEMPLATE_PATH = ThreadLocal.withInitial(String::new);
        TEST_METHODS = ThreadLocal.withInitial(ArrayList::new);
    }

    public static void clean() {
        TEST_MODE.set(MkTestTemplateMode.DEFAULT);
        TEMPLATE_PATH.remove();
        TEST_METHODS.remove();
    }

    public static void setTestMode(final MkTestTemplateMode testMode) {
        TEST_MODE.set(testMode);
    }

    public static void setTemplatePath(final String sourcePath) {
        TEMPLATE_PATH.set(sourcePath);
    }

    public static void setTestMethods(final List<Method> methods) {
        TEST_METHODS.set(methods);
    }

    @Override
    public boolean supportsTestTemplate(final ExtensionContext context) {
        return true;
    }

    // =======================================================
    // Context builder
    // =======================================================

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
            final ExtensionContext context) {
        final Set<String> testPaths = getTestPaths();
        return getInvocationContexts(testPaths);
    }

    private Set<String> getTestPaths() {

        final Path templatePath = getAbsoluteTemplatePath();

        try (final Stream<Path> fileStream = Files.list(templatePath)) {
            return fileStream.filter(Files::isDirectory).map(Path::toString)
                    .collect(Collectors.toSet());

        } catch (final Exception e) {
            final String errorMessage = BASE_ERROR_MESSAGE + "could not get test paths from "
                    + templatePath + " base path";
            throw new MkTestTemplateException(errorMessage, e);
        }
    }


    private Path getAbsoluteTemplatePath() {
        return switch (TEST_MODE.get()) {
            case DEFAULT, TEST -> getClassLoaderPath();
            case INIT, UPDATE -> getResoucesPath();
        };
    }

    private Path getClassLoaderPath() {

        final Path templatePath = Path.of(BASE_RESOURCE_PATH, TEMPLATE_PATH.get());

        try {
            return MkLoadFileUtils.getAbsolutePath(TEMPLATE_PATH.get());
        } catch (final MkException e) {
            final String errorMessage =
                    BASE_ERROR_MESSAGE + "path '/" + TEMPLATE_PATH.get() + "' does not exist";
            throw new MkTestTemplateException(errorMessage, e);
        }
    }

    private Path getResoucesPath() {

        final File templateFile = new File(BASE_RESOURCE_PATH + TEMPLATE_PATH.get());

        if(templateFile.exists()) {
            return templateFile.toPath();
        } else {
            final String errorMessage =
                    BASE_ERROR_MESSAGE + "path '/" + TEMPLATE_PATH.get() + "' does not exist";
            throw new MkTestTemplateException(errorMessage);
        }
    }

    private Stream<TestTemplateInvocationContext> getInvocationContexts(
            final Set<String> testPaths) {
        return testPaths.stream().sorted().flatMap(this::getInvocationContext);
    }

    private Stream<TestTemplateInvocationContext> getInvocationContext(final String testPath) {
        return TEST_METHODS.get().stream().map(
                test -> new MkTestTemplateInvocationContext(TEMPLATE_PATH.get(), testPath, test));
    }

}
