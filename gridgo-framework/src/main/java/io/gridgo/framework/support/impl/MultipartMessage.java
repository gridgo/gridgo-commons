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
        var array = BArray.ofEmpty();
        var count = 0;
        for (Message message : messages) {
            array.add(createObjectFromMessage(message));
            count++;
        }
        var headers = BObject.ofEmpty() //
                             .setAny(MessageConstants.IS_MULTIPART, true) //
                             .setAny(MessageConstants.SIZE, count);
        setPayload(Payload.of(headers, array));
    }

    public MultipartMessage(final @NonNull Collection<Message> messages) {
        var headers = BObject.ofEmpty().setAny(MessageConstants.IS_MULTIPART, true).setAny(MessageConstants.SIZE,
                messages.size());
        var array = BArray.ofEmpty();
        for (Message message : messages) {
            array.add(createObjectFromMessage(message));
        }
        setPayload(Payload.of(headers, array));
    }

    public MultipartMessage(Payload payload) {
        setPayload(payload);
    }

    public Message[] buildOriginalMessages() {
        var size = getPayload().getHeaders().getInteger(MessageConstants.SIZE);
        var messages = new Message[size];
        var body = getPayload().getBody().asArray();
        for (int i = 0; i < body.size(); i++) {
            messages[i] = Message.parse(body.get(i));
        }
        return messages;
    }

    private BElement createObjectFromMessage(Message message) {
        if (message.getPayload() == null)
            return BObject.ofEmpty();
        return message.getPayload().toBArray();
    }
}
