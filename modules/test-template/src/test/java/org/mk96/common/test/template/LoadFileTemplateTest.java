package org.mk96.common.test.template;

import java.awt.Point;
import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.test.template.type.MkTestTemplateType;
import org.mk96.common.util.MkSerializeUtils;

@MkTestTemplateConfiguration(path = "test/template/load", type = MkTestTemplateType.LOAD_EXAMPLE)
class LoadFileTemplateTest extends AbstractMkTestTemplate {

	@MkTestTemplate(exampleName = "ExampleString.json")
	void testString(final String file) {
		super.compareResult(file, new Point(1, 2));
	}

	@MkTestTemplate(exampleName = "ExampleByte.json")
	void testByte(final byte[] file) {
		super.compareResult(file, MkSerializeUtils.writeAsBytes(new Point(1, 2)));
	}

	@MkTestTemplate(exampleName = "ExampleObject.json")
	void testObject(final Point file) {
		super.compareResult(file, new Point(1, 2));
	}

	@MkTestTemplate(exampleName = {
			"ExampleString.json",
			"ExampleByte.json",
			"ExampleObject.json"
	})
	void testMultiParameter(
			final String fileString,
			final byte[] fileByte,
			final Point fileObject) {

		super.compareResult(fileString, new Point(1, 2));
		super.compareResult(fileByte, MkSerializeUtils.writeAsBytes(new Point(1, 2)));
		super.compareResult(fileObject, new Point(1, 2));
	}
}
