package com.markes96.mktest;

import java.awt.Point;
import java.util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.markes96.constant.RegexConstant;
import com.markes96.mktest.template.AbstractMkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplateConfiguration;
import com.markes96.mktest.template.enumeration.TestType;
import com.markes96.utils.MapperUtils;
import lombok.Getter;
import lombok.Setter;

@MkTestTemplateConfiguration(path = "examples/complex")
class CompareResultTemplateTest extends AbstractMkTestTemplate {

  @MkTestTemplate(resultName = "fileString.json")
  String testString(final String path) throws JsonProcessingException {
    return MapperUtils.writeAsString(new Point(1, 2));
  }

  @MkTestTemplate(resultName = "fileByte.json")
  byte[] testByte(final String path) throws JsonProcessingException {
    return MapperUtils.writeAsBytes(new Point(1, 2));
  }

  @MkTestTemplate(resultName = "fileObject.json")
  Point testObject(final String path) throws JsonProcessingException {
    return new Point(1, 2);
  }

  @MkTestTemplate(testType = TestType.FILTER_RESULT, resultName = "filter.json",
      regexForReplace = RegexConstant.UUID)
  String testFilterString(final String path) throws JsonProcessingException {
    return MapperUtils.writeAsString(new ExampleUUID("Peter"));
  }

  @MkTestTemplate(testType = TestType.FILTER_RESULT, resultName = "filter.json",
      regexForReplace = RegexConstant.UUID)
  byte[] testFilterByte(final String path) throws JsonProcessingException {
    return MapperUtils.writeAsBytes(new ExampleUUID("Peter"));
  }

  @MkTestTemplate(testType = TestType.FILTER_RESULT, resultName = "filter.json",
      regexForReplace = RegexConstant.UUID)
  ExampleUUID testFilterObject(final String path) throws JsonProcessingException {
    return new ExampleUUID("Peter");
  }

  @MkTestTemplate(resultName = {"fileString.json", "fileByte.json", "fileObject.json"})
  Object[] testMultiResult(final String path) throws JsonProcessingException {

    final String srt = MapperUtils.writeAsString(new Point(1, 2));
    final byte[] bytes = MapperUtils.writeAsBytes(new Point(1, 2));
    final Point object = new Point(1, 2);

    return super.toArray(srt, bytes, object);
  }

  @MkTestTemplate(testType = TestType.FILTER_RESULT,
      resultName = {"filter.json", "filter.json", "filter.json"},
      regexForReplace = RegexConstant.UUID)
  Object[] testFilterMultiResult(final String path) throws JsonProcessingException {

    final String srt = MapperUtils.writeAsString(new ExampleUUID("Peter"));
    final byte[] bytes = MapperUtils.writeAsBytes(new ExampleUUID("Peter"));
    final ExampleUUID object = new ExampleUUID("Peter");

    return super.toArray(srt, bytes, object);
  }

  @Getter
  @Setter
  public class ExampleUUID {
    private final String name;
    private final UUID uuid = UUID.randomUUID();

    public ExampleUUID(final String name) {
      this.name = name;
    }
  }
}
