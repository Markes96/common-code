package com.markes96.mktest.template.contextprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MkTestTemplateParameterResolver implements ParameterResolver {

  private final MkTestTemplateParameter cadTestTemplateParameter;

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext,
      final ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType() == MkTestTemplateParameter.class;
  }

  @Override
  public Object resolveParameter(final ParameterContext parameterContext,
      final ExtensionContext extensionContext) {
    return this.cadTestTemplateParameter;
  }
}
