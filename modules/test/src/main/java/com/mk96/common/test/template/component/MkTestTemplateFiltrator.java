package com.mk96.common.test.template.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mk96.common.test.AbstractMkTest;

public class MkTestTemplateFiltrator {

  public Object[] filter(final Object[] results, final String[] regex)
      throws JsonProcessingException {

    if (ArrayUtils.isEmpty(regex))
      return results;

    final List<Object> resultsFiltered = new ArrayList<>();
    for (final Object result : results) {
      resultsFiltered.add(AbstractMkTest.filterResult(result, regex));
    }

    return resultsFiltered.toArray();
  }

}
