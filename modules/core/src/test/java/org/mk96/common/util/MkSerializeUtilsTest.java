package org.mk96.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mk96.common.util.requirements.FileObject;

public class MkSerializeUtilsTest {

	@Test
	void read() {

		// String
		final String filesString = MkLoadFileUtils.load(String.class, "util", "serialize", "example.json");
		final FileObject fileFromString = MkSerializeUtils.read(FileObject.class, filesString);
		Assertions.assertEquals(filesString, fileFromString.toString());

		// Bytes
		final byte[] filesBytes = MkLoadFileUtils.load(byte[].class, "util", "serialize", "example.json");
		final FileObject fileFromBytes = MkSerializeUtils.read(FileObject.class, filesBytes);
		Assertions.assertEquals(filesString, fileFromBytes.toString());

	}

}
