package com.markes96.mktest.template.contextprovider;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import com.markes96.mktest.template.exception.MkTestTemplateException;
import com.markes96.utils.LoadFileUtils;

public class MkTestTemplateInvocationContextProvider
    implements TestTemplateInvocationContextProvider {

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

  private static String getSourcePath() {
    return SOURCE_PATH.get();
  }

  public static void setTestMethods(final List<Method> methods) {
    TEST_METHODS.set(methods);
  }

  private static List<Method> getTestMethods() {
    return TEST_METHODS.get();
  }

  @Override
  public boolean supportsTestTemplate(final ExtensionContext context) {
    return true;
  }

  private Path getTemplatePath() {
    try {
      return LoadFileUtils.getAbsolutePath(getSourcePath());
    } catch (final Exception e) {
      final String ErrorMessage =
          "Error loading invocation contexts: path '/" + getSourcePath() + "' doesn't exist";
      throw new MkTestTemplateException(ErrorMessage, e);
    }
  }

  private Set<String> getValuesPaths(final Path templatePath) {

    try (final Stream<Path> fileStream = Files.walk(templatePath)) {
      return fileStream.map(path -> (path.toFile().isFile()) ? path.toFile().getParent() : null)
          .filter(Objects::nonNull).collect(Collectors.toSet());

    } catch (final Exception e) {
      final String ErrorMessage =
          "Error loading invocation contexts: error geting value paths processing " + templatePath
              + " template path";
      throw new MkTestTemplateException(ErrorMessage, e);
    }
  }

  private Stream<TestTemplateInvocationContext> getInvocationContext(final String testPath) {
    return getTestMethods().stream().map(test -> this.testPathContext(test, testPath));
  }

  private Stream<TestTemplateInvocationContext> getInvocationContexts(final Set<String> testPaths) {
    return testPaths.stream().sorted().flatMap(this::getInvocationContext);
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
      final ExtensionContext context) {

    final Path templatePath = this.getTemplatePath();
    final Set<String> testPaths = this.getValuesPaths(templatePath);
    return this.getInvocationContexts(testPaths);
  }

  private TestTemplateInvocationContext testPathContext(final Method test, final String path) {
    return new TestTemplateInvocationContext() {

      @Override
      public String getDisplayName(final int invocationIndex) {

        final String slashPath = path.replace("\\", "/");
        final String displayPath =
            getSourcePath() + StringUtils.substringAfterLast(slashPath, getSourcePath());
        return "Processing: [Path -> '.../" + displayPath + "'] :: [Test -> '" + test.getName()
            + "']";
      }

      @Override
      public List<Extension> getAdditionalExtensions() {
        return Arrays
            .asList(new MkTestTemplateParameterResolver(new MkTestTemplateParameter(test, path)));
      }
    };
  }
}
