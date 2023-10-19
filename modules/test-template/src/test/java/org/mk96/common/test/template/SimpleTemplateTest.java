package org.mk96.common.test.template;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.test.template.type.MkTestTemplateType;

@MkTestTemplateConfiguration(path = "test/template/simple")
class SimpleTemplateTest extends AbstractMkTestTemplate {

	@MkTestTemplate
	void baseTest() {
		Assertions.assertTrue(true);
	}

	@MkTestTemplate(type = MkTestTemplateType.LOAD_PATH)
	void loadPathTest(final String sourcePath) {
		Assertions.assertTrue(StringUtils.isNotEmpty(sourcePath));
	}
}
