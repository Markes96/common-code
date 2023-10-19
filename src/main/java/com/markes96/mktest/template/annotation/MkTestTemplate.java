package com.markes96.mktest.template.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.markes96.mktest.template.AbstractMkTestTemplate;
import com.markes96.mktest.template.enumeration.TestType;

/**
 * <p>
 * Annotation indicates that a method is a test template in a class that implements
 * {@linkplain AbstractCadTestTemplate}
 * </p>
 *
 * <ul>
 * <li>{@link #testType()} default value {@link TestType}.DEFAULT</li>
 * <li>{@link #exampleName()} default value {@value #DEFAULT_EXAMPLE_NAME}</li>
 * <li>{@link #resultName()} default value {@value #DEFAULT_RESULT_NAME}</li>
 * <li>{@link #regexForReplace()} default value {@value #DEFAULT_REGEX_FOR_REPLACE}</li>
 * </ul>
 *
 * @see AbstractCadTestTemplate
 * @see MkTestTemplateConfiguration
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MkTestTemplate {

  String DEFAULT_EXAMPLE_NAME = AbstractMkTestTemplate.DEFAULT_EXAMPLE_NAME;
  String DEFAULT_RESULT_NAME = AbstractMkTestTemplate.DEFAULT_RESULT_NAME;
  String DEFAULT_REGEX_FOR_REPLACE = AbstractMkTestTemplate.DEFAULT_REGEX_FOR_REPLACE;

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
  String[] exampleName() default {};

  /**
   * <p>
   * {@link String} parameter used to define result name file. Default value
   * {@value #DEFAULT_RESULT_NAME}
   * </p>
   *
   * @return result name file
   */
  String[] resultName() default {};

  /**
   * <p>
   * {@link String} array parameter used to define regex for replace. Default value
   * {@value #DEFAULT_REGEX_FOR_REPLACE}
   * </p>
   *
   * @return regex for replace array
   */
  String[] regexForReplace() default {};

}
