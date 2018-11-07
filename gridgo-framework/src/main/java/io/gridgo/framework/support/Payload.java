package io.gridgo.framework.support;

import java.util.Optional;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.impl.DefaultPayload;

public interface Payload {

	public Optional<BValue> getId();

	public BObject getHeaders();

	public BElement getBody();

	public static Payload newDefault(BValue id, BElement body) {
		return new DefaultPayload(Optional.of(id), body);
	}

	public static Payload newDefault(BValue id, BObject headers, BElement body) {
		return new DefaultPayload(Optional.of(id), headers, body);
	}

	public static Payload newDefault(BObject headers, BElement body) {
		return new DefaultPayload(headers, body);
	}
	
	public static Payload newDefault(BElement body) {
		return new DefaultPayload(body);
	}
}
