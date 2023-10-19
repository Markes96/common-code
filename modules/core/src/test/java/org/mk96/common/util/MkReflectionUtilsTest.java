package org.mk96.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mk96.common.util.requirements.FileObject;

public class MkReflectionUtilsTest {

	@Test
	void findFieldTest() {

		final Field field = MkReflectionUtils.findField(FileObject.class, "message");
		Assertions.assertNotNull(field);

		final Class<?> type = MkReflectionUtils.findFieldType(FileObject.class, "message");
		Assertions.assertNotNull(type);

		final FileObject example = new FileObject("Helo world");
		final Object value = MkReflectionUtils.findFieldValue(example, "message");
		Assertions.assertNotNull(value);

	}

	@Test
	void findMethodTest() {

		final Field field = MkReflectionUtils.findField(FileObject.class, "message");

		final Method getterField = MkReflectionUtils.findGetter(FileObject.class, field);
		Assertions.assertNotNull(getterField);

		final Method getterString = MkReflectionUtils.findGetter(FileObject.class, "message");
		Assertions.assertNotNull(getterString);

		final Method setterField = MkReflectionUtils.findSetter(FileObject.class, field);
		Assertions.assertNotNull(setterField);

		final Method setterString = MkReflectionUtils.findSetter(FileObject.class, "message");
		Assertions.assertNotNull(setterString);

		final Method method = MkReflectionUtils.findMethod(FileObject.class, "getMessage");
		Assertions.assertNotNull(method);

		Assertions.assertThrows(RuntimeException.class,
				() -> MkReflectionUtils.findMethod(FileObject.class, "getMessage", Integer.class));

	}

	@Test
	void invokeMethodTest() throws InvocationTargetException {

		final FileObject fileObject = new FileObject();

		MkReflectionUtils.invokeMethod(fileObject, "setMessage", "Helo world");

		final Method getter = MkReflectionUtils.findGetter(FileObject.class, "message");
		final Object result = MkReflectionUtils.invokeMethod(fileObject, getter);

		Assertions.assertEquals("Helo world", result);

	}

}
