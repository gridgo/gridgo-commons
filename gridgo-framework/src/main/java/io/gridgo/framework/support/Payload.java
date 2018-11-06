package io.gridgo.framework.support;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;

public interface Payload {

	public BValue getId();

	public BObject getHeaders();

	public BElement getBody();

	static Payload newDefault() {
		return new DefaultPayload();
	}

	static Payload newDefault(BElement body) {
		DefaultPayload payload = (DefaultPayload) newDefault();
		payload.setBody(body);
		return payload;
	}
}
