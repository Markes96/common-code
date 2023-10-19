package org.mk96.common.test.template.constant;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MkTestTemplateConstant {

	public final String DEFAULT_PATH = "template";

	public final String DEFAULT_EXAMPLE_NAME = "example.json";
	public final String DEFAULT_RESULT_NAME = "result.json";
	public final String DEFAULT_REGEX_FOR_REPLACE = StringUtils.EMPTY;

	public final Comparator<Method> TEST_COMPARATOR = new Comparator<Method>() {

		@Override
		public int compare(final Method method1, final Method method2) {
			return StringUtils.compare(method1.getName(), method2.getName());
		}

	};

}
