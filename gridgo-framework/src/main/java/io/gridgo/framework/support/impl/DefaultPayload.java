package io.gridgo.framework.support.impl;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.Payload;
import lombok.Getter;

@Getter
public class DefaultPayload implements Payload {

	private BValue id;

	private BObject headers;
	
	private BElement body;

	public DefaultPayload(BValue id, BObject headers, BElement body) {
		this.id = id;
		this.headers = headers;
		this.body = body;
	}

	public DefaultPayload(BValue id, BElement body) {
		this.id = id;
		this.body = body;
		this.headers = BObject.newDefault();
	}
}
