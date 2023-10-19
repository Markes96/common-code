package org.mk96.common.test.template.type;

import java.lang.reflect.Method;

import org.mk96.common.type.MkObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MkTestTemplateParameter extends MkObject {

	private final String path;
	private final Method test;

}
