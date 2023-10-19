package com.mk96.common.test.template;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import com.mk96.common.test.template.annotation.MkTestTemplate;
import com.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import com.mk96.common.test.template.type.MkTestTemplateType;

@MkTestTemplateConfiguration(path = "test/template/simple")
class SimpleTemplateTest extends AbstractMkTestTemplate {

  @MkTestTemplate
  void baseTest() {
    Assertions.assertTrue(true);
  }

  @MkTestTemplate(testType = MkTestTemplateType.LOAD_PATH)
  void loadPathTest(final String sourcePath) {
    Assertions.assertTrue(StringUtils.isNotEmpty(sourcePath));
  }
}
