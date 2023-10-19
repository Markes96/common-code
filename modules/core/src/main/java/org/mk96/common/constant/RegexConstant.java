package org.mk96.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexConstant {

	public final String UUID = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
	public final String DEFAULT_WINDOWS_UNIX_DATE = "\\([0-9]{4}\\/[0-9]{2}\\/[0-9]{2} [0-9]{2}\\:[0-9]{2}\\:[0-9]{2} (AM|PM|a. m.|p. m.) [a-zA-Z0-9]*(?:Z|[+-](?:2[0-3]|[01][0-9]):[0-5][0-9])*\\)";

}
