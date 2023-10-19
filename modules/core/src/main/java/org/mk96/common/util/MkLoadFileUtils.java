package org.mk96.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mk96.common.constant.CommonConstant;
import org.mk96.common.type.MkException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkLoadFileUtils {

	/**
	 * Get absolute {@link Path} from relative {@link String} path
	 *
	 * @param path -> relative path
	 * @param more -> relative path to join
	 * @return Absolute {@link Path}
	 */
	public Path getAbsolutePath(final String path, final String... more) {
		final String moreAsPath = String.join(CommonConstant.SLASH, more);
		final String relativePath = StringUtils.isEmpty(moreAsPath) ? path
				: String.join(CommonConstant.SLASH, path, moreAsPath);

		final ClassLoader context = Thread.currentThread().getContextClassLoader();
		final URL url = context.getResource(relativePath);

		if (Objects.isNull(url)) {
            throw new MkLoadFileException("Resource '" + relativePath + "' was not found");
        }

		final File file = new File(url.getFile());
		return Paths.get(file.getAbsolutePath());
	}

	/**
	 * Load file from relative {@link String} path on a {@link Byte} array,
	 * {@link String} or {@link T} object
	 *
	 * @param <T>      deserialize type
	 * @param type     -> indicates return type
	 * @param filePath -> file to load path
	 * @param more     -> file to load path to join
	 * @return File loaded on a {@link Byte} array, {@link String} or {@link T}
	 *         object
	 */
	public <T> T load(final Class<T> type, final String filePath, final String... more) {
		return load(type, getAbsolutePath(filePath, more));
	}

	/**
	 * Load {@link Path} file on a {@link Byte} array, {@link String} or {@link T}
	 * object
	 *
	 * @param <T>        deserialize type
	 * @param type       -> indicates return type
	 * @param fileToLoad -> file to load {@link Path}
	 * @return File loaded on a {@link Byte} array, {@link String} or {@link T}
	 *         object
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(final Class<T> type, final Path fileToLoad) {

		if (type == byte[].class) {
            return (T) loadAsBytes(fileToLoad);
        }

		if (type == String.class) {
            return (T) loadAsString(fileToLoad);
        }

		return loadAsObject(type, fileToLoad);
	}

	/**
	 * Load {@link Path} file on a {@link Byte} array
	 *
	 * @param fileToLoad -> file to load {@link Path}
	 * @return File loaded on a {@link Byte} array
	 */
	public byte[] loadAsBytes(final Path fileToLoad) {

		try {
			return FileUtils.readFileToByteArray(fileToLoad.toFile());
		} catch (final IOException e) {
			throw new MkLoadFileException("Error reading file '" + fileToLoad + "'", e);
		}
	}

	/**
	 * Load {@link Path} file on a {@link String}
	 *
	 * @param fileToLoad -> file to load {@link Path}
	 * @return File loaded on a {@link String}
	 */
	public String loadAsString(final Path fileToLoad) {

		try {
			return FileUtils.readFileToString(fileToLoad.toFile(), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			throw new MkLoadFileException("Error reading file '" + fileToLoad + "'", e);
		}
	}

	/**
	 * Load {@link Path} file on a {@link T} object
	 *
	 * @param <T>        deserialize type
	 * @param type       -> indicates return type
	 * @param fileToLoad -> file to load {@link Path}
	 * @return File loaded on a {@link T} object
	 */
	public <T> T loadAsObject(final Class<T> type, final Path fileToLoad) {

		try {
			final String fileString = FileUtils.readFileToString(fileToLoad.toFile(), StandardCharsets.UTF_8);
			return MkSerializeUtils.read(type, fileString);
		} catch (final IOException e) {
			throw new MkLoadFileException("Error reading file '" + fileToLoad + "'", e);
		}
	}

	public class MkLoadFileException extends MkException {

		private static final long serialVersionUID = 1L;

		public MkLoadFileException(final String message) {
			super(message);
		}

		public MkLoadFileException(final String message, final Throwable e) {
			super(message, e);
		}

	}

}
