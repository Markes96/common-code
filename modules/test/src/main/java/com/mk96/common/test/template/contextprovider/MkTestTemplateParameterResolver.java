package com.mk96.common.test.template.contextprovider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import com.mk96.common.test.template.type.MkTestTemplateParameter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MkTestTemplateParameterResolver implements ParameterResolver {

  private final MkTestTemplateParameter testTemplateParameter;

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext,
      final ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType() == MkTestTemplateParameter.class;
  }

  @Override
  public Object resolveParameter(final ParameterContext parameterContext,
      final ExtensionContext extensionContext) {
    return testTemplateParameter;
  }
}
