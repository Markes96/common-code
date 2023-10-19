package com.markes96.mktest;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import com.markes96.mktest.template.AbstractMkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplate;
import com.markes96.mktest.template.annotation.MkTestTemplateConfiguration;

@MkTestTemplateConfiguration(path = "examples/simple", test = "noAnnotated")
class SimpleTemplateTest extends AbstractMkTestTemplate {

  void noAnnotated(final String sourcePath) {
    Assertions.assertTrue(StringUtils.isNotEmpty(sourcePath));
  }

  @MkTestTemplate
  void annotated(final String sourcePath) {
    Assertions.assertTrue(StringUtils.isNotEmpty(sourcePath));
  }
}
