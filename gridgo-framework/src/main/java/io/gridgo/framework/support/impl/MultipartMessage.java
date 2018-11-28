package io.gridgo.framework.support.impl;

import java.util.Arrays;
import java.util.Collection;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.framework.support.Message;
import io.gridgo.framework.support.MessageConstants;
import io.gridgo.framework.support.Payload;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MultipartMessage extends DefaultMessage {

	public MultipartMessage(final @NonNull Message[] messages) {
		this(Arrays.asList(messages));
	}

	public MultipartMessage(final @NonNull Iterable<Message> messages) {
		var array = BArray.newDefault();
		var count = 0;
		for (Message message : messages) {
			array.add(createObjectFromMessage(message));
			count++;
		}
		var headers = BObject.newDefault() //
				.setAny(MessageConstants.IS_MULTIPART, true) //
				.setAny(MessageConstants.SIZE, count);
		setPayload(Payload.newDefault(headers, array));
	}

	public MultipartMessage(final @NonNull Collection<Message> messages) {
		var headers = BObject.newDefault().setAny(MessageConstants.IS_MULTIPART, true).setAny(MessageConstants.SIZE,
				messages.size());
		var array = BArray.newDefault();
		for (Message message : messages) {
			array.add(createObjectFromMessage(message));
		}
		setPayload(Payload.newDefault(headers, array));
	}

	private BElement createObjectFromMessage(Message message) {
		if (message.getPayload() == null)
			return BObject.newDefault();
		return message.getPayload().toBArray();
	}
}
