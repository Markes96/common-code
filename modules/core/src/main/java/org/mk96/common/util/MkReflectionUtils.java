package org.mk96.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.mk96.common.type.MkException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MkReflectionUtils {

	// =======================================================
	// Field finders
	// =======================================================

	public Field findField(final Class<?> type, final String FieldName) {

		try {
			return type.getDeclaredField(FieldName);
		} catch (final NoSuchFieldException e) {
			throw new MkException("Field \"" + FieldName + "' not finded on class '" + type.getSimpleName() + "'", e);
		}

	}

	public Class<?> findFieldType(final Class<?> type, final String FieldName) {
		final Field field = findField(type, FieldName);
		return field.getType();
	}

	public Object findFieldValue(final Object obj, final String fieldName) {

		final Field field = findField(obj.getClass(), fieldName);
		return findFieldValue(obj, field);
	}

	public Object findFieldValue(final Object obj, final Field field) {

		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (final IllegalArgumentException e) {
			throw new MkException(
					"Field '" + field.getName() + "' not found on class '" + obj.getClass().getSimpleName() + "\"", e);
		} catch (final IllegalAccessException e) {
			throw new MkException("Field '" + field.getName() + "' is not accesible on class '"
					+ obj.getClass().getSimpleName() + "'", e);
		}

	}

	// =======================================================
	// Method finders
	// =======================================================

	public Method findGetter(final Class<?> type, final Field field) {
		final String getterName = "get" + StringUtils.capitalize(field.getName());
		return findMethod(type, getterName);
	}

	public <T> Method findGetter(final Class<T> type, final String fieldName) {
		final String getterName = "get" + StringUtils.capitalize(fieldName);
		return findMethod(type, getterName);
	}

	public <T> Method findSetter(final Class<T> type, final Field field) {
		final String getterName = "set" + StringUtils.capitalize(field.getName());
		final Class<?> fieldType = field.getType();

		return findMethod(type, getterName, fieldType);
	}

	public <T> Method findSetter(final Class<T> type, final String fieldName) {
		final String getterName = "set" + StringUtils.capitalize(fieldName);
		final Class<?> fieldType = findFieldType(type, fieldName);

		return findMethod(type, getterName, fieldType);
	}

	public Method findMethod(final Class<?> type, final String methodName, final Class<?>... params) {

		try {
			return type.getDeclaredMethod(methodName, params);
		} catch (final NoSuchMethodException e) {
			final List<String> paramsString = Stream.of(params).map(Class::getSimpleName).toList();
			throw new MkException(
					"Method '" + methodName + paramsString + "' was not found on class '" + type.getSimpleName() + "'",
					e);
		}
	}

	// =======================================================
	// Method invokers
	// =======================================================

	public Object invokeMethod(final Object obj, final String methodName, final Object... params)
			throws InvocationTargetException {

		final Class<?>[] paramTypes = getTypes(params);
		final Method method = findMethod(obj.getClass(), methodName, paramTypes);
		return invokeMethod(obj, method, params);

	}

	public Object invokeMethod(final Object obj, final Method method, final Object... params)
			throws InvocationTargetException {

		try {
			method.setAccessible(true);
			return method.invoke(obj, params);
		} catch (final IllegalArgumentException | IllegalAccessException e) {

			final List<String> methodParamTypes = Stream.of(method.getParameterTypes()).map(Class::getCanonicalName)
					.toList();

			final List<String> paramTypes = Stream.of(params).map(Object::getClass).map(Class::getSimpleName).toList();

			throw new MkException(
					"Could not invoke method '" + method.getName() + methodParamTypes + "' with params " + paramTypes,
					e);
		}

	}

	public Class<?>[] getTypes(final Object[] objs) {

		final Class<?>[] types = new Class<?>[objs.length];
		for (int i = 0; i < objs.length; i++) {
			types[i] = objs[i].getClass();
		}

		return types;
	}

}
