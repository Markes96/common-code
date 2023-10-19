package com.markes96.mktest;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import com.markes96.mktest.template.AbstractMkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplateConfiguration;
import com.markes96.mktest.template.enumeration.TestType;

@MkTestTemplateConfiguration(path = "examples/simple",
    test = "MkTestTemplateConfigurationDefaultValues")
class AnnotationValuesTest extends AbstractMkTestTemplate {

  void MkTestTemplateConfigurationDefaultValues(final String sourcePath)
      throws NoSuchMethodException, SecurityException {

    final MkTestTemplate MkTestTemplate =
        this.getClass().getDeclaredMethod("MkTestTemplateConfigurationDefaultValues", String.class)
            .getAnnotation(MkTestTemplate.class);

    final List<String> expectedExampleName =
        Arrays.asList(AbstractMkTestTemplate.DEFAULT_EXAMPLE_NAME);
    final List<String> exampleName = Arrays.asList(super.getExampleNames(MkTestTemplate));
    Assertions.assertEquals(expectedExampleName, exampleName);

    final List<String> expectedResultName =
        Arrays.asList(AbstractMkTestTemplate.DEFAULT_RESULT_NAME);
    final List<String> resultName = Arrays.asList(super.getResultNames(MkTestTemplate));
    Assertions.assertEquals(expectedResultName, resultName);

    final List<TestType> expectedTestType = Arrays.asList(AbstractMkTestTemplate.DEFAULT_TEST_TYPE);
    final List<TestType> testType = Arrays.asList(super.getTestType(MkTestTemplate));
    Assertions.assertEquals(expectedTestType, testType);

    final List<String> expectedRegexForReplace =
        Arrays.asList(AbstractMkTestTemplate.DEFAULT_REGEX_FOR_REPLACE);
    final List<String> regexForReplace = Arrays.asList(super.getRegexForReplace(MkTestTemplate));
    Assertions.assertEquals(expectedRegexForReplace, regexForReplace);
  }

  @MkTestTemplate
  void MkTestTemplateDefaultValues(final String sourcePath)
      throws NoSuchMethodException, SecurityException {

    final MkTestTemplate MkTestTemplate =
        this.getClass().getDeclaredMethod("MkTestTemplateDefaultValues", String.class)
            .getAnnotation(MkTestTemplate.class);

    final List<String> expectedExampleName =
        Arrays.asList(AbstractMkTestTemplate.DEFAULT_EXAMPLE_NAME);
    final List<String> exampleName = Arrays.asList(super.getExampleNames(MkTestTemplate));
    Assertions.assertEquals(expectedExampleName, exampleName);

    final List<String> expectedResultName =
        Arrays.asList(AbstractMkTestTemplate.DEFAULT_RESULT_NAME);
    final List<String> resultName = Arrays.asList(super.getResultNames(MkTestTemplate));
    Assertions.assertEquals(expectedResultName, resultName);

    final List<TestType> expectedTestType = Arrays.asList(AbstractMkTestTemplate.DEFAULT_TEST_TYPE);
    final List<TestType> testType = Arrays.asList(super.getTestType(MkTestTemplate));
    Assertions.assertEquals(expectedTestType, testType);

    final List<String> expectedRegexForReplace =
        Arrays.asList(AbstractMkTestTemplate.DEFAULT_REGEX_FOR_REPLACE);
    final List<String> regexForReplace = Arrays.asList(super.getRegexForReplace(MkTestTemplate));
    Assertions.assertEquals(expectedRegexForReplace, regexForReplace);
  }

  @MkTestTemplate(testType = TestType.BASIC, exampleName = "exampleName", resultName = "resultName",
      regexForReplace = "regexForReplace")
  void MkTestTemplateValues(final String sourcePath)
      throws NoSuchMethodException, SecurityException {

    final MkTestTemplate MkTestTemplate =
        this.getClass().getDeclaredMethod("MkTestTemplateValues", String.class)
            .getAnnotation(MkTestTemplate.class);

    final List<String> expectedExampleName = Arrays.asList("exampleName");
    final List<String> exampleName = Arrays.asList(super.getExampleNames(MkTestTemplate));
    Assertions.assertEquals(expectedExampleName, exampleName);

    final List<String> expectedResultName = Arrays.asList("resultName");
    final List<String> resultName = Arrays.asList(super.getResultNames(MkTestTemplate));
    Assertions.assertEquals(expectedResultName, resultName);

    final List<TestType> expectedTestType = Arrays.asList(TestType.BASIC);
    final List<TestType> testType = Arrays.asList(super.getTestType(MkTestTemplate));
    Assertions.assertEquals(expectedTestType, testType);

    final List<String> expectedRegexForReplace = Arrays.asList("regexForReplace");
    final List<String> regexForReplace = Arrays.asList(super.getRegexForReplace(MkTestTemplate));
    Assertions.assertEquals(expectedRegexForReplace, regexForReplace);
  }
}
