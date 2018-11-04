package io.gridgo.framework.support;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;

public interface Payload {

	public BValue getId();

	public BObject getHeaders();

	public BElement getBody();
}
