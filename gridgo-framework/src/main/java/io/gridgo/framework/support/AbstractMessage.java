package io.gridgo.framework.support;

import java.util.Map;

import io.gridgo.bean.BValue;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMessage implements Message {

	private BValue routingId;
	private Map<String, Object> misc;
	private Payload payload;

	public void setRoutingId(@NonNull Object routingId) {
		if (routingId instanceof BValue) {
			this.routingId = (BValue) routingId;
		} else {
			this.routingId = BValue.newDefault(routingId);
		}
	}

}
