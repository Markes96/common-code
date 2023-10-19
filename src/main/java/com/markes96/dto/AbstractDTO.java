package com.markes96.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.markes96.utils.MapperUtils;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public abstract class AbstractDTO {

  @Override
  public String toString() {
    try {
      return MapperUtils.writeAsString(this);
    } catch (final JsonProcessingException e) {
      return e.toString();
    }
  }

}
