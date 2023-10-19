package org.mk96.common.test.template.handler.initialize.example;

import org.mk96.common.test.template.handler.initialize.MkTestTemplateFileInitalizeHandler;
import org.mk96.common.test.template.type.MkTestTemplateExecution;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkTestTemplateExampleFileInitalizeHandler {

	public void init(final MkTestTemplateExecution execution) {
	    MkTestTemplateFileInitalizeHandler.createFiles(execution.getPath(), execution.getExampleNames());
	}

}
