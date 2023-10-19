package com.markes96.mktest.template.exception;

public class MkTestTemplateException extends RuntimeException {

  public MkTestTemplateException(final String mesagge) {
    super(mesagge);
  }

  public MkTestTemplateException(final String mesagge, final Throwable cause) {
    super(mesagge, cause);
  }
}
