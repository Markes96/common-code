package com.markes96.mktest.template;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.markes96.mktest.AbstractMkTest;
import com.markes96.mktest.template.annotation.MkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplateConfiguration;
import com.markes96.mktest.template.contextprovider.MkTestTemplateInvocationContextProvider;
import com.markes96.mktest.template.contextprovider.MkTestTemplateParameter;
import com.markes96.mktest.template.enumeration.TestType;
import com.markes96.mktest.template.exception.MkTestTemplateException;
import com.markes96.utils.LoadFileUtils;
import com.markes96.utils.MapperUtils;
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

  public static final String DEFAULT_PATH = "template";
  public static final String DEFAULT_TEST = "templateTest";
  public static final String DEFAULT_EXAMPLE_NAME = "example.json";
  public static final String DEFAULT_RESULT_NAME = "result.json";
  public static final TestType DEFAULT_TEST_TYPE = TestType.DEFAULT;
  public static final String DEFAULT_REGEX_FOR_REPLACE = StringUtils.EMPTY;

  private static final Comparator<Method> methodComparator = new MethodComparator();

  private List<Method> testMethods = new ArrayList<>();

  private String[] exampleName = {DEFAULT_EXAMPLE_NAME};
  private String[] resultName = {DEFAULT_RESULT_NAME};
  private TestType testType = DEFAULT_TEST_TYPE;
  private String[] regexForReplace = {DEFAULT_REGEX_FOR_REPLACE};

  private String sourcePath = StringUtils.EMPTY;

  @BeforeAll
  void configTemplate() {

    final MkTestTemplateConfiguration templateConfiguration =
        this.getClass().getAnnotation(MkTestTemplateConfiguration.class);
    this.sourcePath = StringUtils.EMPTY;

    final List<String> testList;
    if (Objects.nonNull(templateConfiguration)) {
      MkTestTemplateInvocationContextProvider.setSourcePath(templateConfiguration.path());
      testList = Arrays.asList(templateConfiguration.test());
      this.exampleName = templateConfiguration.exampleName();
      this.resultName = templateConfiguration.resultName();
      this.testType = templateConfiguration.testType();
      this.regexForReplace = templateConfiguration.regexForReplace();
    } else {
      MkTestTemplateInvocationContextProvider.setSourcePath(DEFAULT_PATH);
      testList = Arrays.asList(DEFAULT_TEST);
    }

    final Predicate<Method> isConfiguratedAsTest = method -> testList.contains(method.getName());
    final Predicate<Method> isAnnotatedAsTest =
        method -> Objects.nonNull(method.getAnnotation(MkTestTemplate.class));
    final Predicate<Method> isTest = isConfiguratedAsTest.or(isAnnotatedAsTest);

    final Method[] methods = this.getClass().getDeclaredMethods();
    this.testMethods =
        Stream.of(methods).sorted(methodComparator).filter(isTest).collect(Collectors.toList());

    MkTestTemplateInvocationContextProvider.setTestMethods(this.testMethods);
  }

  @AfterAll
  void resetTemplate() {
    MkTestTemplateInvocationContextProvider.clean();
    this.sourcePath = StringUtils.EMPTY;
  }

  @TestTemplate
  @ExtendWith(MkTestTemplateInvocationContextProvider.class)
  void mkTestTemplateExecutor(final MkTestTemplateParameter mkTestTemplateParameter)
      throws IOException, IllegalAccessException, InvocationTargetException {

    final Method test = mkTestTemplateParameter.getTest();
    this.sourcePath = mkTestTemplateParameter.getPath();

    this.invokeAndCompare(test);
  }

  private void invokeAndCompare(final Method test)
      throws IOException, IllegalAccessException, InvocationTargetException {

    final MkTestTemplate mkTestTemplate = test.getAnnotation(MkTestTemplate.class);

    test.setAccessible(true);
    final Object testResult = this.invokeTest(test, mkTestTemplate);

    if (test.getReturnType() != void.class) {
      final Object[] testResultArray = this.convertToComparableArray(testResult);
      this.compareTestResults(testResultArray, mkTestTemplate);
    }
  }

  private Object[] convertToComparableArray(final Object obj) {
    return obj instanceof Object[] ? (Object[]) obj : super.toArray(obj);
  }

  private Object invokeTest(final Method test, final MkTestTemplate mkTestTemplate)
      throws IOException, IllegalAccessException, InvocationTargetException {

    final TestType type = this.getTestType(mkTestTemplate);

    if (type.loadFile) {
      final String[] filesToLoad = this.getExampleNames(mkTestTemplate);
      final Object[] args = this.loadFiles(test.getParameterTypes(), filesToLoad);
      return test.invoke(this, args);
    }
    return test.invoke(this, this.sourcePath);
  }

  private Object[] loadFiles(final Class<?>[] args, final String[] fileNames) throws IOException {

    if (args.length > fileNames.length) {
      throw new MkTestTemplateException(
          "Number of test arguments must be less or equal than number of ExampleNames");
    }

    final List<Object> files = new ArrayList<>();
    for (int i = 0; i < args.length; i++) {
      files.add(LoadFileUtils.load(args[i], this.sourcePath, fileNames[i]));
    }

    return files.toArray();
  }

  private void compareTestResults(final Object[] testResults, final MkTestTemplate mkTestTemplate)
      throws IOException {

    final String[] fileNames = this.getResultNames(mkTestTemplate);
    if (testResults.length > fileNames.length) {
      throw new MkTestTemplateException(
          "Number of test results must be less or equal than number of ResultNames");
    }
    for (int i = 0; i < testResults.length; i++) {
      this.compareTestResult(testResults[i], fileNames[i], mkTestTemplate);
    }

  }

  private void compareTestResult(Object testResult, final String fileName,
      final MkTestTemplate mkTestTemplate) throws IOException {

    final TestType type = this.getTestType(mkTestTemplate);

    if (type.filter) {
      final String[] regex = this.getRegexForReplace(mkTestTemplate);
      testResult = this.filterTestResult(testResult, regex);
    }

    super.compareResultToFile(testResult, this.sourcePath, fileName);
  }

  protected Object filterTestResult(final Object testResult, final String[] regex)
      throws JsonProcessingException {
    final Object result =
        (testResult instanceof byte[]) || (testResult instanceof String) ? testResult
            : MapperUtils.writeAsString(testResult);
    return super.filterResult(result, Arrays.asList(regex));
  }

  protected String[] getExampleNames(final MkTestTemplate mkTestTemplate) {
    return (Objects.nonNull(mkTestTemplate) && ArrayUtils.isNotEmpty(mkTestTemplate.exampleName()))
        ? mkTestTemplate.exampleName()
        : this.exampleName;
  }

  protected String[] getResultNames(final MkTestTemplate mkTestTemplate) {
    return (Objects.nonNull(mkTestTemplate) && ArrayUtils.isNotEmpty(mkTestTemplate.resultName()))
        ? mkTestTemplate.resultName()
        : this.resultName;
  }

  protected TestType getTestType(final MkTestTemplate mkTestTemplate) {
    return (Objects.nonNull(mkTestTemplate) && (DEFAULT_TEST_TYPE != mkTestTemplate.testType()))
        ? mkTestTemplate.testType()
        : this.testType;
  }

  protected String[] getRegexForReplace(final MkTestTemplate mkTestTemplate) {
    return (Objects.nonNull(mkTestTemplate)
        && ArrayUtils.isNotEmpty(mkTestTemplate.regexForReplace()))
            ? mkTestTemplate.regexForReplace()
            : this.regexForReplace;
  }

  private static class MethodComparator implements Comparator<Method>, Serializable {
    @Override
    public int compare(final Method method1, final Method method2) {
      return StringUtils.compare(method1.getName(), method2.getName());
    }
  }


}
