package org.mk96.common.test.template;

import java.awt.Point;
import org.junit.jupiter.api.Assertions;
import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.test.template.type.MkTestTemplateType;

@MkTestTemplateConfiguration(path = "test/template/init")
class InitTemplateTest extends AbstractMkTestTemplate {

	@MkTestTemplate(type = MkTestTemplateType.LOAD_EXAMPLE)
	Point baseTest(String example) {
		Assertions.assertTrue(true);
		return new Point(2, 3);
	}

}
