package io.gridgo.framework.support;

import java.io.InputStream;
import java.nio.ByteBuffer;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BElement;
import io.gridgo.utils.wrapper.ByteBufferInputStream;

public interface MessageParser {

	static final MessageParser DEFAULT = new MessageParser() {
	};

	default Message parse(byte[] bytes) {
		return parse(ByteBuffer.wrap(bytes));
	}

	default Message parse(ByteBuffer buffer) {
		return parse(new ByteBufferInputStream(buffer));
	}

	default Message parse(InputStream inputStream) {
		BElement data = BElement.fromRaw(inputStream);
		return parse(data);
	}

	default Message parse(BElement data) {
		Payload payload = null;

		if (data instanceof BArray && data.asArray().size() == 3) {
			BArray arr = data.asArray();
			BElement id = arr.get(0);
			
			BElement headers = arr.get(1);
			if (headers.isValue() && headers.asValue().isNull()) {
				headers = null;
			}
			
			BElement body = arr.get(2);
			if (body.isValue() && body.asValue().isNull()) {
				body = null;
			}

			if (id.isValue() && (headers == null || headers.isObject())) {
				payload = Payload.newDefault(id.asValue(), headers == null ? null : headers.asObject(), body);
			}
		}

		if (payload == null) {
			payload = Payload.newDefault(data);
		}

		return Message.newDefault(payload);
	}
}
