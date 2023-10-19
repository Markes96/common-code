package org.mk96.common.test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.mk96.common.util.MkLoadFileUtils;
import org.mk96.common.util.MkSerializeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractMkTest {

	/**
	 * Method used to filter an object using a string list of regex patterns
	 *
	 * @param <T>             the type of the value being boxed
	 * @param result          value wanted to be filtered
	 * @param regexForReplace pattern list used to filter
	 * @return object filtered
	 * @throws MkTestException         when Argument result isn´t a String or byte[]
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 */
	@SuppressWarnings("unchecked")
	public static <T> T filterResult(final T result, final String... regexForReplace) {

		String resultString;

		if (result instanceof String) {
			resultString = (String) result;
		} else if (result instanceof byte[]) {
			resultString = new String((byte[]) result);
		} else {
			resultString = MkSerializeUtils.writeAsString(result);
		}

		resultString = Stream.of(regexForReplace).reduce(resultString,
				(acumulate, regex) -> acumulate.replaceAll(regex, StringUtils.EMPTY));

		if (result instanceof String)
			return (T) resultString;
		if (result instanceof byte[])
			return (T) resultString.getBytes();

		return (T) MkSerializeUtils.read(result.getClass(), resultString);
	}

	/**
	 * Method used to compare an {@link Object} to a file passed by a {@link String}
	 * path
	 *
	 * @param result             Object to compare
	 * @param expectedResultPath the path string or initial part of the path string
	 *                           of the file
	 * @param more               additional strings to be joined to form the path
	 *                           string
	 * @return boolean of the comparison result
	 * @throws IOException     when an I/O exception of some sort has occurred
	 * @throws MkTestException if there is an error while comparing the result to
	 *                         the file
	 */
	public static void compareResultToFile(final Object result, final String expectedResultPath,
			final String... more) {
		compareResultToFile(result, MkLoadFileUtils.getAbsolutePath(expectedResultPath, more));
	}

	/**
	 * Method used to compare an Object to a file passed as {@link Path}
	 *
	 * @param result             Object to be compared
	 * @param expectedResultPath Path of the file to be compared with
	 * @return boolean as result of the comparison
	 * @throws MkTestException when there is an error while loading the file
	 * @throws IOException     when an I/O exception of some sort has occurred
	 */
	public static void compareResultToFile(final Object result, final Path expectedResultPath) {
		final Class<?> type = result instanceof byte[] ? byte[].class : String.class;
		final Object expectedResult = MkLoadFileUtils.load(type, expectedResultPath);
		compareResult(result, expectedResult);
	}

	/**
	 * Method to compare two objects
	 *
	 * @param result         object to compare
	 * @param expectedResult object expected to compare
	 * @return boolean of the result from the comparison
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 */
	public static void compareResult(final Object result, final Object expectedResult) {

		final Object resultComparable = convertToComparableType(result);
		final Object expectedComparable = convertToComparableType(expectedResult);

		Assertions.assertEquals(expectedComparable, resultComparable);
	}

	/**
	 * Method used to convert into a comparable type the {@link Object} passed
	 *
	 * @param object to be converted
	 * @return the object converted into a String or byte[]
	 * @throws JsonProcessingException Intermediate base class for all problems
	 *                                 encountered when processing (parsing,
	 *                                 generating) JSON content that are not pure
	 *                                 I/O problems
	 */
	public static Object convertToComparableType(final Object object) {

		if (object instanceof String)
			return object;

		if (object instanceof final byte[] bytes)
			return convertByteArray(bytes);

		return MkSerializeUtils.writeAsString(object);
	}

	public static List<Byte> convertByteArray(final byte[] bytes) {
		final List<Byte> comparableByte = new ArrayList<>();
		for (final byte b : bytes) {
			comparableByte.add(Byte.valueOf(b));
		}
		return comparableByte;
	}

	/**
	 * Convert to array
	 *
	 * @param <T> the type of the value being boxed
	 * @param t   objecs to intert on array
	 * @return array with all parameters
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(final T... t) {
		return t;
	}

}
