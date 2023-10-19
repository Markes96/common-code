package com.markes96.dto.api.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markes96.dto.api.ApiResponseDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiOkResponseDTO<T> extends ApiResponseDTO {

  @JsonUnwrapped
  private T response;

}
