package com.markes96.dto.api;

import com.markes96.dto.AbstractDTO;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class ApiRequestDataDTO extends AbstractDTO {

}
