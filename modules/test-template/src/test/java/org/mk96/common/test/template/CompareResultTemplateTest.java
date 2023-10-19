package org.mk96.common.test.template;

import java.awt.Point;

import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.util.MkSerializeUtils;

@MkTestTemplateConfiguration(path = "test/template/compare")
class CompareResultTemplateTest extends AbstractMkTestTemplate {

	@MkTestTemplate(resultName = "ResultString.json")
	String testString() {
		return MkSerializeUtils.writeAsString(new Point(1, 2));
	}

	@MkTestTemplate(resultName = "ResultByte.json")
	byte[] testByte() {
		return MkSerializeUtils.writeAsBytes(new Point(1, 2));
	}

	@MkTestTemplate(resultName = "ResultObject.json")
	Point testObject() {
		return new Point(1, 2);
	}

	@MkTestTemplate(resultName = {
			"ResultString.json",
			"ResultByte.json",
			"ResultObject.json"
	})
	Object[] testMultiResult() {

		final String srt = MkSerializeUtils.writeAsString(new Point(1, 2));
		final byte[] bytes = MkSerializeUtils.writeAsBytes(new Point(1, 2));
		final Point object = new Point(1, 2);

		return super.toArray(srt, bytes, object);
	}

}
