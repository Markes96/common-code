package com.markes96.mktest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.markes96.utils.LoadFileUtils;
import com.markes96.utils.MapperUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractMkTest {

  /**
   * Method used to filter an object using a string list of regex patterns
   *
   * @param <T> the type of the value being boxed
   * @param result value wanted to be filtered
   * @param regexForReplace pattern list used to filter
   * @return object filtered
   * @throws MkTestException when Argument result isn´t a String or byte[]
   * @throws JsonProcessingException Intermediate base class for all problems encountered when
   *         processing (parsing, generating) JSON content that are not pure I/O problems
   */
  @SuppressWarnings("unchecked")
  protected <T> T filterResult(final T result, final List<String> regexForReplace)
      throws JsonProcessingException {

    String resultString;

    if (result instanceof String) {
      resultString = (String) result;
    } else if (result instanceof byte[]) {
      resultString = new String((byte[]) result);
    } else {
      resultString = MapperUtils.writeAsString(result);
    }

    resultString = regexForReplace.stream().reduce(resultString,
        (acumulate, regex) -> acumulate.replaceAll(regex, StringUtils.EMPTY));

    if (result instanceof String) {
      return (T) resultString;
    }
    if (result instanceof byte[]) {
      return (T) resultString.getBytes();
    }
    return (T) MapperUtils.readFromString(result.getClass(), resultString);
  }

  /**
   * Method used to compare an {@link Object} to a file passed by a {@link String} path
   *
   * @param result Object to compare
   * @param expectedResultPath the path string or initial part of the path string of the file
   * @param more additional strings to be joined to form the path string
   * @return boolean of the comparison result
   * @throws IOException when an I/O exception of some sort has occurred
   * @throws MkTestException if there is an error while comparing the result to the file
   */
  protected boolean compareResultToFile(final Object result, final String expectedResultPath,
      final String... more) throws IOException {
    return this.compareResultToFile(result, Paths.get(expectedResultPath, more));
  }

  /**
   * Method used to compare an Object to a file passed as {@link Path}
   *
   * @param result Object to be compared
   * @param expectedResultPath Path of the file to be compared with
   * @return boolean as result of the comparison
   * @throws MkTestException when there is an error while loading the file
   * @throws IOException when an I/O exception of some sort has occurred
   */
  protected boolean compareResultToFile(final Object result, final Path expectedResultPath)
      throws IOException {
    final Class<?> clazz = result instanceof byte[] ? byte[].class : String.class;
    final Object expectedResult = LoadFileUtils.load(clazz, expectedResultPath);
    return this.compareResult(result, expectedResult);
  }

  /**
   * Method to compare two objects
   *
   * @param result object to compare
   * @param expectedResult object expected to compare
   * @return boolean of the result from the comparison
   * @throws JsonProcessingException Intermediate base class for all problems encountered when
   *         processing (parsing, generating) JSON content that are not pure I/O problems
   */
  protected boolean compareResult(final Object result, final Object expectedResult)
      throws JsonProcessingException {

    final Object resultComparable = this.convertToComparableType(result);
    final Object expectedComparable = this.convertToComparableType(expectedResult);

    Assertions.assertEquals(expectedComparable, resultComparable);
    return resultComparable.equals(expectedComparable);
  }

  /**
   * Method used to convert into a comparable type the {@link Object} passed
   *
   * @param object to be converted
   * @return the object converted into a String or byte[]
   * @throws JsonProcessingException Intermediate base class for all problems encountered when
   *         processing (parsing, generating) JSON content that are not pure I/O problems
   */
  protected Object convertToComparableType(final Object object) throws JsonProcessingException {

    if (object instanceof String) {
      return object;
    }
    if (object instanceof byte[]) {
      return CollectionUtils.arrayToList(object);
    }
    return MapperUtils.writeAsString(object);
  }

  /**
   * Convert to array
   *
   * @param <T> the type of the value being boxed
   * @param t objecs to intert on array
   * @return array with all parameters
   */
  protected <T> T[] toArray(final T... t) {
    return t;
  }

}
