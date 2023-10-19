package org.mk96.common.test.template.requirements;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleUUID {
	private String name = "Peter";
	private UUID uuid = UUID.randomUUID();
}
