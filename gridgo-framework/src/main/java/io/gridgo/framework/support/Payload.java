package io.gridgo.framework.support;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.impl.DefaultPayload;

public interface Payload {

	public BValue getId();

	public BObject getHeaders();

	public BElement getBody();

	public static Payload newDefault(BValue id, BElement body) {
		return new DefaultPayload(id, body);
	}

	public static Payload newDefault(BValue id, BObject headers, BElement body) {
		return new DefaultPayload(id, headers, body);
	}
}
