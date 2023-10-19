package com.markes96.mktest;

import java.awt.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.markes96.mktest.template.AbstractMkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplateConfiguration;
import com.markes96.mktest.template.enumeration.TestType;
import com.markes96.utils.MapperUtils;

@MkTestTemplateConfiguration(path = "examples/complex", testType = TestType.LOAD_FILE)
class LoadFileTemplateTest extends AbstractMkTestTemplate {

  @MkTestTemplate(exampleName = "fileString.json")
  void testString(final String file) throws JsonProcessingException {
    super.compareResult(file, MapperUtils.writeAsString(new Point(1, 2)));
  }

  @MkTestTemplate(exampleName = "fileByte.json")
  void testByte(final byte[] file) throws JsonProcessingException {
    super.compareResult(file, MapperUtils.writeAsBytes(new Point(1, 2)));
  }

  @MkTestTemplate(exampleName = "fileObject.json")
  void testObject(final Point file) throws JsonProcessingException {
    super.compareResult(file, new Point(1, 2));
  }

  @MkTestTemplate(exampleName = {"fileString.json", "fileByte.json", "fileObject.json"})
  void testMultiParameter(final String fileString, final byte[] fileByte, final Point fileObject)
      throws JsonProcessingException {

    super.compareResult(fileString, new Point(1, 2));
    super.compareResult(fileByte, MapperUtils.writeAsBytes(new Point(1, 2)));
    super.compareResult(fileObject, new Point(1, 2));
  }
}
