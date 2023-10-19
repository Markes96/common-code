package org.mk96.common.test.template.exception;

import org.mk96.common.type.MkException;

public class MkTestTemplateException extends MkException {

	private static final long serialVersionUID = 1L;

	public MkTestTemplateException(final String message) {
		super(message);
	}

	public MkTestTemplateException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
