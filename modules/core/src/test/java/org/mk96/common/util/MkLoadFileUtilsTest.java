package org.mk96.common.util;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mk96.common.util.requirements.FileObject;

public class MkLoadFileUtilsTest {

	@Test
	void getAbsolutePathTest() {

		final Path completePath = MkLoadFileUtils.getAbsolutePath("util/loadfile/getAbsolutePathExist.json");
		Assertions.assertNotNull(completePath);

		final Path partialPath = MkLoadFileUtils.getAbsolutePath("util", "loadfile", "getAbsolutePathExist.json");
		Assertions.assertNotNull(partialPath);

		Assertions.assertThrows(RuntimeException.class,
				() -> MkLoadFileUtils.getAbsolutePath("util", "loadfile", "getAbsolutePathNoExist.json"));

	}

	@Test
	void loadTest() {

		final String filesString = MkLoadFileUtils.load(String.class, "util", "loadfile", "loadFileExample.json");
		Assertions.assertNotNull(filesString);

		final byte[] fileByteArray = MkLoadFileUtils.load(byte[].class, "util", "loadfile", "loadFileExample.json");
		Assertions.assertNotNull(fileByteArray);

		final FileObject fileObject = MkLoadFileUtils.load(FileObject.class, "util", "loadfile", "loadFileExample.json");
		Assertions.assertNotNull(fileObject);

	}

}
