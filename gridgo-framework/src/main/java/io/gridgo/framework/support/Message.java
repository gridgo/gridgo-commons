package io.gridgo.framework.support;

import java.util.Map;
import java.util.Optional;

import io.gridgo.bean.BValue;
import io.gridgo.framework.support.impl.DefaultMessage;

public interface Message {

	/**
	 * routingId use for *-to-1 communication like duplex socket. RoutingId indicate
	 * which endpoint will be the target
	 * 
	 * @return the routing id
	 */
	public Optional<BValue> getRoutingId();

	public Map<String, Object> getMisc();

	public Payload getPayload();

	static Message newDefault(Payload payload) {
		return new DefaultMessage(payload);
	}

	static Message newDefault(BValue routingId, Payload payload) {
		return new DefaultMessage(routingId, payload);
	}

	static Message newDefault(BValue routingId, Map<String, Object> misc, Payload payload) {
		return new DefaultMessage(routingId, misc, payload);
	}
}
