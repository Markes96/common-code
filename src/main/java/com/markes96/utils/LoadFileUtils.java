package com.markes96.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import com.markes96.constant.CommonConstant;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoadFileUtils {

  /**
   * Method used to get the absolute {@link Path} representation of the {@link String} passed by
   * parameter
   *
   * @param relativePath representation in String of the relative path of a file
   * @return Absolute path {@link Path} representation of the {@link String} passed by parameter
   */
  public Path getAbsolutePath(final String relativePath) {
    return Paths.get(
        new File(Thread.currentThread().getContextClassLoader().getResource(relativePath).getFile())
            .getAbsolutePath());
  }

  /**
   * Method used to load a file with a string of the file path
   *
   * @param <T> the type of the value being boxed
   * @param clazz class that indicates the return type of the method
   * @param path string or initial part of the path string
   * @param more additional strings to be joined to form the path string
   * @return string or byte[] of the file loaded
   * @throws IOException when an I/O exception of some sort has occurred
   */
  public <T> T loadFromRelativePath(final Class<T> clazz, final String path,
      final String... more) throws IOException {
    final String moreAsPath = String.join(CommonConstant.SLASH, more);
    final String relativePath = String.join(CommonConstant.SLASH, path, moreAsPath);
    return load(clazz, getAbsolutePath(relativePath));
  }

  /**
   * Method used to load a file with a string of the file path
   *
   * @param <T> the type of the value being boxed
   * @param clazz class that indicates the return type of the method
   * @param path string or initial part of the path string
   * @param more additional strings to be joined to form the path string
   * @return string or byte[] of the file loaded
   * @throws IOException when an I/O exception of some sort has occurred
   */
  public <T> T load(final Class<T> clazz, final String path, final String... more)
      throws IOException {
    return load(clazz, Paths.get(path, more));
  }

  /**
   * Method used to load a file with a {@link Class} and {@link Path} passed
   *
   * @param <T> the type of the value being boxed
   * @param clazz class that indicates the return type of the method
   * @param fileToLoad object Path of the file to load
   * @return a string or byte[] of the file loaded
   * @throws IOException when an I/O exception of some sort has occurred
   */
  @SuppressWarnings("unchecked")
  public <T> T load(final Class<T> clazz, final Path fileToLoad) throws IOException {

    T fileLoaded = null;

    if (clazz == byte[].class) {
      fileLoaded = (T) FileUtils.readFileToByteArray(fileToLoad.toFile());
    } else {
      final String fileString =
          FileUtils.readFileToString(fileToLoad.toFile(), StandardCharsets.UTF_8);
      if (clazz == String.class) {
        fileLoaded = (T) fileString;
      } else {
        fileLoaded = MapperUtils.readFromString(clazz, fileString);
      }
    }

    return fileLoaded;
  }

}
