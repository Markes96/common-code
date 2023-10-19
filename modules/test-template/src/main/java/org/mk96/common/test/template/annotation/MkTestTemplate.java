package org.mk96.common.test.template.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mk96.common.test.template.AbstractMkTestTemplate;
import org.mk96.common.test.template.constant.MkTestTemplateConstant;
import org.mk96.common.test.template.type.MkTestTemplateMode;
import org.mk96.common.test.template.type.MkTestTemplateType;

/**
 * <p>
 * Annotation indicates that a method is a test template in a class that
 * implements {@linkplain AbstractMkTestTemplate}
 * </p>
 *
 * <ul>
 * <li>{@link #testType()} default value {@link MkTestTemplateType}.DEFAULT</li>
 * <li>{@link #exampleName()} default value {@value #DEFAULT_EXAMPLE_NAME}</li>
 * <li>{@link #resultName()} default value {@value #DEFAULT_RESULT_NAME}</li>
 * <li>{@link #regexForReplace()} default value
 * {@value #DEFAULT_REGEX_FOR_REPLACE}</li>
 * </ul>
 *
 * @see AbstractMkTestTemplate
 * @see MkTestTemplateConfiguration
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MkTestTemplate {

	String DEFAULT_EXAMPLE_NAME = MkTestTemplateConstant.DEFAULT_EXAMPLE_NAME;
	String DEFAULT_RESULT_NAME = MkTestTemplateConstant.DEFAULT_RESULT_NAME;
	String DEFAULT_REGEX_FOR_REPLACE = MkTestTemplateConstant.DEFAULT_REGEX_FOR_REPLACE;

	/**
	 * <p>
	 * {@link MkTestTemplateType} parameter used to define test type. Default value
	 * {@link MkTestTemplateType}.DEFAULT
	 * </p>
	 *
	 * @return test type
	 */
	MkTestTemplateType type() default MkTestTemplateType.DEFAULT;

	/**
     * <p>
     * {@link MkTestTemplateMode} parameter used to define test mode. Default value
     * {@link MkTestTemplateMode}.DEFAULT
     * </p>
     *
     * @return test type
     */
    MkTestTemplateMode mode() default MkTestTemplateMode.DEFAULT;

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
	 * {@link String} array parameter used to define regex for replace. Default
	 * value {@value #DEFAULT_REGEX_FOR_REPLACE}
	 * </p>
	 *
	 * @return regex for replace array
	 */
	String[] regexForReplace() default {};

}
