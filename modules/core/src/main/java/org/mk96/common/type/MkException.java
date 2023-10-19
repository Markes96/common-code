package org.mk96.common.type;

import org.mk96.common.constant.CommonConstant;

public class MkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MkException(final Throwable e) {
		super(e);
	}

	public MkException(final String message) {
		super(message);
	}

	public MkException(final String message, final Throwable e) {
		super(message, e);
	}

	@Override
	public String getLocalizedMessage() {

		String buffer = "";

		buffer += CommonConstant.CARRIAGE;
		buffer += CommonConstant.ARROW;
		buffer += getMessage();

		final Throwable cause = getCause();
		if (getCause() != null) {
			buffer += CommonConstant.CARRIAGE;
			buffer += cause.toString();
		}

		return buffer;
	}

}
