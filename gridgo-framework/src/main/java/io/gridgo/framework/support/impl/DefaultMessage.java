package io.gridgo.framework.support.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.gridgo.bean.BValue;
import io.gridgo.framework.support.Message;
import io.gridgo.framework.support.Payload;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class DefaultMessage implements Message {

	private Map<String, Object> misc = new HashMap<>();
	private Optional<BValue> routingId = Optional.empty();
	private Payload payload;

	public DefaultMessage(Payload payload) {
		this.payload = payload;
	}

	public void setRoutingId(@NonNull Object routingId) {
		if (routingId instanceof BValue) {
			this.routingId = Optional.of((BValue) routingId);
		} else {
			this.routingId = Optional.of(BValue.newDefault(routingId));
		}
	}
}
