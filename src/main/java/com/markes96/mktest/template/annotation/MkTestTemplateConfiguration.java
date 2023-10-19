package com.markes96.mktest.template.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.markes96.mktest.template.AbstractMkTestTemplate;
import com.markes96.mktest.template.enumeration.TestType;

/**
 * <p>
 * Annotation used to configure an implementation of {@linkplain AbstractCadTestTemplate} at the
 * class level
 * </p>
 *
 * <ul>
 * <li>{@link #path()} default value {@value #DEFAULT_PATH}</li>
 * <li>{@link #test()} default value {@value #DEFAULT_TEST}</li>
 *
 * <li>{@link #testType()} default value {@link TestType}.DEFAULT</li>
 * <li>{@link #exampleName()} default value {@value #DEFAULT_EXAMPLE_NAME}</li>
 * <li>{@link #resultName()} default value {@value #DEFAULT_RESULT_NAME}</li>
 * <li>{@link #regexForReplace()} default value {@value #DEFAULT_REGEX_FOR_REPLACE}</li>
 * </ul>
 *
 * @see AbstractCadTestTemplate
 * @see MkTestTemplate
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MkTestTemplateConfiguration {

  String DEFAULT_PATH = AbstractMkTestTemplate.DEFAULT_PATH;
  String DEFAULT_TEST = AbstractMkTestTemplate.DEFAULT_TEST;
  String DEFAULT_EXAMPLE_NAME = AbstractMkTestTemplate.DEFAULT_EXAMPLE_NAME;
  String DEFAULT_RESULT_NAME = AbstractMkTestTemplate.DEFAULT_RESULT_NAME;
  String DEFAULT_REGEX_FOR_REPLACE = AbstractMkTestTemplate.DEFAULT_REGEX_FOR_REPLACE;

  /**
   * <p>
   * {@link String} parameter used to define main folder to test template. Default value
   * {@value #DEFAULT_PATH}
   * </p>
   *
   * @return main folder to test
   */
  String path() default DEFAULT_PATH;

  /**
   * <p>
   * {@link String} parameter used to define names of test templates. Default value
   * {@value #DEFAULT_TEST}
   * </p>
   *
   * @return names of test templates
   */
  String[] test() default DEFAULT_TEST;

  /**
   * <p>
   * {@link TestType} parameter used to define test type. Default value {@link TestType}.DEFAULT
   * </p>
   *
   * @return test type
   */
  TestType testType() default TestType.DEFAULT;

  /**
   * <p>
   * {@link String} parameter used to define example name file. Default value
   * {@value #DEFAULT_EXAMPLE_NAME}
   * </p>
   *
   * @return example name file
   */
  String[] exampleName() default DEFAULT_EXAMPLE_NAME;

  /**
   * <p>
   * {@link String} parameter used to define result name file. Default value
   * {@value #DEFAULT_RESULT_NAME}
   * </p>
   *
   * @return result name file
   */
  String[] resultName() default DEFAULT_RESULT_NAME;

  /**
   * <p>
   * {@link String} array parameter used to define regex for replace. Default value
   * {@value #DEFAULT_REGEX_FOR_REPLACE}
   * </p>
   *
   * @return regex for replace array
   */
  String[] regexForReplace() default {DEFAULT_REGEX_FOR_REPLACE};
}
