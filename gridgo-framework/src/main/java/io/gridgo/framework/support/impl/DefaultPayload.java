package io.gridgo.framework.support.impl;

import java.util.Optional;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.Payload;
import lombok.Getter;

@Getter
public class DefaultPayload implements Payload {

	private Optional<BValue> id = Optional.empty();

	private BObject headers;
	
	private BElement body;

	public DefaultPayload(Optional<BValue> id, BObject headers, BElement body) {
		this.id = id;
		this.headers = headers;
		this.body = body;
	}

	public DefaultPayload(BObject headers, BElement body) {
		this.headers = headers;
		this.body = body;
	}

	public DefaultPayload(Optional<BValue> id, BElement body) {
		this.id = id;
		this.body = body;
		this.headers = BObject.newDefault();
	}
	
	public DefaultPayload(BElement body) {
		this.body = body;
		this.headers = BObject.newDefault();
	}
}
