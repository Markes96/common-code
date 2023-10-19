package com.markes96.dto.api.response;

import java.time.LocalDateTime;
import com.markes96.dto.api.ApiResponseDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ApiErrorResponseDTO extends ApiResponseDTO {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private int status;
  private String error;
  private String message;

  public ApiErrorResponseDTO(final Exception e, final int status) {
    this.status = status;
    this.error = e.getLocalizedMessage();
    this.message = e.getMessage();
  }

}
