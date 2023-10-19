package com.mk96.common.test.template.contextprovider;

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
import com.mk96.common.test.template.exception.MkTestTemplateException;
import com.mk96.common.type.MkException;
import com.mk96.common.util.MkLoadFileUtils;

public class MkTestTemplateInvocationContextProvider
    implements TestTemplateInvocationContextProvider {

  private final String BASE_ERROR_MESSAGE = "Error loading invocation context: ";

  // =======================================================
  // Parametrization
  // =======================================================

  private static final ThreadLocal<String> SOURCE_PATH;
  private static final ThreadLocal<List<Method>> TEST_METHODS;

  static {
    SOURCE_PATH = ThreadLocal.withInitial(String::new);
    TEST_METHODS = ThreadLocal.withInitial(ArrayList::new);
  }

  public static void clean() {
    SOURCE_PATH.remove();
    TEST_METHODS.remove();
  }

  public static void setSourcePath(final String sourcePath) {
    SOURCE_PATH.set(sourcePath);
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

    final Path templatePath = getAbsoluteSourcePath();

    try (final Stream<Path> fileStream = Files.walk(templatePath)) {
      return fileStream
          .map(Path::toFile)
          .filter(File::isFile)
          .map(File::getParent)
          .collect(Collectors.toSet());

    } catch (final Exception e) {
      final String errorMessage =
          BASE_ERROR_MESSAGE + "could not get test paths from " + templatePath
              + " base path";
      throw new MkTestTemplateException(errorMessage, e);
    }
  }

  private Path getAbsoluteSourcePath() {
    try {
      return MkLoadFileUtils.getAbsolutePath(SOURCE_PATH.get());
    } catch (final MkException e) {
      final String errorMessage =
          BASE_ERROR_MESSAGE + "path '/" + SOURCE_PATH.get() + "' does not exist";
      throw new MkTestTemplateException(errorMessage, e);
    }
  }

  private Stream<TestTemplateInvocationContext> getInvocationContexts(final Set<String> testPaths) {
    return testPaths.stream().sorted().flatMap(this::getInvocationContext);
  }

  private Stream<TestTemplateInvocationContext> getInvocationContext(final String testPath) {
    return TEST_METHODS.get().stream()
        .map(test -> new MkTestTemplateInvocationContext(SOURCE_PATH.get(), testPath, test));
  }

}
