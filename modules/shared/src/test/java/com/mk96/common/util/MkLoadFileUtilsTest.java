package com.mk96.common.util;

import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.mk96.common.util.requirements.FileObject;

public class MkLoadFileUtilsTest {

  @Test
  void getAbsolutePathTest() {

    final Path compleatPath =
        MkLoadFileUtils.getAbsolutePath("utils/loadfile/getAbsolutePathExist.json");

    Assertions.assertNotNull(compleatPath);

    final Path partialPath =
        MkLoadFileUtils.getAbsolutePath("utils", "loadfile", "getAbsolutePathExist.json");
    Assertions.assertNotNull(partialPath);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> MkLoadFileUtils.getAbsolutePath("utils", "loadfile", "getAbsolutePathNoExist.json"));

  }

  @Test
  void loadTest() {

    final String filesString =
        MkLoadFileUtils.load(String.class, "utils", "loadfile", "loadFileExample.json");
    Assertions.assertNotNull(filesString);

    final byte[] fileByteArray =
        MkLoadFileUtils.load(byte[].class, "utils", "loadfile", "loadFileExample.json");
    Assertions.assertNotNull(fileByteArray);

    final FileObject fileObject =
        MkLoadFileUtils.load(FileObject.class, "utils", "loadfile", "loadFileExample.json");
    Assertions.assertNotNull(fileObject);

  }

}
