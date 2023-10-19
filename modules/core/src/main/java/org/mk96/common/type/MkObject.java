package org.mk96.common.type;

import org.mk96.common.util.MkSerializeUtils;

public abstract class MkObject {

	@Override
	public String toString() {
		return MkSerializeUtils.writeAsString(this);
	}

}
