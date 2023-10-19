package org.mk96.common.test.template;

import org.mk96.common.constant.RegexConstant;
import org.mk96.common.test.template.annotation.MkTestTemplate;
import org.mk96.common.test.template.annotation.MkTestTemplateConfiguration;
import org.mk96.common.test.template.requirements.ExampleUUID;
import org.mk96.common.util.MkSerializeUtils;

@MkTestTemplateConfiguration(path = "test/template/filter")
public class FilterResultTemplateTest extends AbstractMkTestTemplate {

	@MkTestTemplate(resultName = "ResultString.json", regexForReplace = RegexConstant.UUID)
	String testFilterString() {
		return MkSerializeUtils.writeAsString(new ExampleUUID());
	}

	@MkTestTemplate(resultName = "ResultByte.json", regexForReplace = RegexConstant.UUID)
	byte[] testFilterByte() {
		return MkSerializeUtils.writeAsBytes(new ExampleUUID());
	}

	@MkTestTemplate(resultName = "ResultObject.json", regexForReplace = RegexConstant.UUID)
	ExampleUUID testFilterObject() {
		return new ExampleUUID();
	}

	@MkTestTemplate(resultName = {
			"ResultString.json",
			"ResultByte.json",
			"ResultObject.json"
	}, regexForReplace = RegexConstant.UUID)
	Object[] testFilterMultiResult() {

		final String srt = MkSerializeUtils.writeAsString(new ExampleUUID());
		final byte[] bytes = MkSerializeUtils.writeAsBytes(new ExampleUUID());
		final ExampleUUID object = new ExampleUUID();

		return super.toArray(srt, bytes, object);
	}

}
