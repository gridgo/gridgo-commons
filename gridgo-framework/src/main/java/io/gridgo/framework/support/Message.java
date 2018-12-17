package io.gridgo.framework.support;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.impl.DefaultMessage;
import io.gridgo.framework.support.impl.MultipartMessage;
import io.gridgo.utils.wrapper.ByteBufferInputStream;

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

    public Message addMisc(String key, Object value);

    public Message setRoutingId(BValue routingId);

    public default Message setRoutingIdFromAny(Object routingId) {
        this.setRoutingId(BValue.of(routingId));
        return this;
    }

    public default Message attachSource(String name) {
        if (name != null)
            getMisc().putIfAbsent(MessageConstants.SOURCE, name);
        return this;
    }

    public Message setPayload(Payload payload);

    static Message ofEmpty() {
        return of(Payload.ofEmpty());
    }

    static Message ofAny(Object body) {
        return of(Payload.of(BElement.ofAny(body)));
    }

    static Message ofAny(BObject headers, Object body) {
        return of(Payload.of(headers, BElement.ofAny(body)));
    }

    static Message of(Payload payload) {
        return new DefaultMessage(payload);
    }

    static Message of(BValue routingId, Payload payload) {
        return new DefaultMessage(routingId, payload);
    }

    static Message of(BValue routingId, Map<String, Object> misc, Payload payload) {
        return new DefaultMessage(routingId, misc, payload);
    }

    static Message parse(byte[] bytes) {
        return parse(ByteBuffer.wrap(bytes));
    }

    static Message parse(ByteBuffer buffer) {
        return parse(new ByteBufferInputStream(buffer));
    }

    static Message parse(InputStream inputStream) {
        BElement data = BElement.ofBytes(inputStream);
        return parse(data);
    }

    static Message parse(BElement data) {
        Payload payload = null;

        var multipart = false;

        if (data instanceof BArray && data.asArray().size() == 3) {
            BArray arr = data.asArray();
            BElement id = arr.get(0);

            BElement headers = arr.get(1);
            if (headers.isValue() && headers.asValue().isNull()) {
                headers = BObject.ofEmpty();
            }
            multipart = headers.asObject().getBoolean(MessageConstants.IS_MULTIPART, false);

            BElement body = arr.get(2);
            if (body.isValue() && body.asValue().isNull()) {
                body = null;
            }

            if (id.isValue() && (headers == null || headers.isObject())) {
                payload = Payload.of(id.asValue(), headers.asObject(), body);
            }
        }

        if (payload == null) {
            payload = Payload.of(data);
        }

        if (multipart)
            return new MultipartMessage(payload);

        return Message.of(payload);
    }
}
