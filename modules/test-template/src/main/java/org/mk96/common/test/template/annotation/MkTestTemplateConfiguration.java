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
 * Annotation used to configure an implementation of
 * {@linkplain AbstractMKTestTemplate} at the class level
 * </p>
 *
 * <ul>
 * <li>{@link #path()} default value {@value #DEFAULT_PATH}</li>
 * <li>{@link #test()} default value {@value #DEFAULT_TEST}</li>
 *
 * <li>{@link #testType()} default value {@link MkTestTemplateType}.DEFAULT</li>
 * <li>{@link #exampleName()} default value {@value #DEFAULT_EXAMPLE_NAME}</li>
 * <li>{@link #resultName()} default value {@value #DEFAULT_RESULT_NAME}</li>
 * <li>{@link #regexForReplace()} default value
 * {@value #DEFAULT_REGEX_FOR_REPLACE}</li>
 * </ul>
 *
 * @see AbstractMkTestTemplate
 * @see MkTestTemplate
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MkTestTemplateConfiguration {

	String DEFAULT_PATH = MkTestTemplateConstant.DEFAULT_PATH;
	String DEFAULT_EXAMPLE_NAME = MkTestTemplateConstant.DEFAULT_EXAMPLE_NAME;
	String DEFAULT_RESULT_NAME = MkTestTemplateConstant.DEFAULT_RESULT_NAME;
	String DEFAULT_REGEX_FOR_REPLACE = MkTestTemplateConstant.DEFAULT_REGEX_FOR_REPLACE;

	/**
	 * <p>
	 * {@link String} parameter used to define main folder to test template. Default
	 * value {@value #DEFAULT_PATH}
	 * </p>
	 *
	 * @return main folder to test
	 */
	String path();

	/**
	 * <p>
	 * {@link MkTestTemplateType} parameter used to define if load file mode is
	 * enabled. Default false {@link MkTestTemplateType}.DEFAULT
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
