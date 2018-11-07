package io.gridgo.framework.support;

import java.util.HashMap;
import java.util.Map;

import io.gridgo.bean.BValue;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class DefaultMessage implements Message {

	private Map<String, Object> misc = new HashMap<>();
	private BValue routingId;
	private Payload payload;
	
	public DefaultMessage(Payload payload) {
		this.payload = payload;
	}

	public void setRoutingId(@NonNull Object routingId) {
		if (routingId instanceof BValue) {
			this.routingId = (BValue) routingId;
		} else {
			this.routingId = BValue.newDefault(routingId);
		}
	}
}
