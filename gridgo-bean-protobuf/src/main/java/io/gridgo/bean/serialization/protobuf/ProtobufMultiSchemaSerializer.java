package io.gridgo.bean.serialization.protobuf;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.serialization.AbstractMultiSchemaSerializer;
import io.gridgo.bean.serialization.BSerializationPlugin;

@BSerializationPlugin(ProtobufMultiSchemaSerializer.NAME)
public class ProtobufMultiSchemaSerializer extends AbstractMultiSchemaSerializer<MessageLite> implements ProtobufSerializer {

    public static final String NAME = "protobufMultiSchema";

    private final Map<Integer, Method> parseFromMethodCache = new NonBlockingHashMap<>();

    public ProtobufMultiSchemaSerializer() {
        super(MessageLite.class);
    }

    @Override
    protected void onSchemaRegistered(Class<? extends MessageLite> schema, int id) {
        this.parseFromMethodCache.putIfAbsent(id, extractParseFromMethod(schema));
    }

    @Override
    protected void onSchemaDeregistered(Class<? extends MessageLite> schema, int id) {
        this.parseFromMethodCache.remove(id);
    }

    private Method getParseFromMethodOf(int id) throws Exception {
        return parseFromMethodCache.get(id);
    }

    @Override
    public void doSerialize(Integer id, MessageLite msgObj, OutputStream out) throws Exception {
        msgObj.writeTo(out);
    }

    @Override
    public Object doDeserialize(InputStream in, Integer id) throws Exception {
        return getParseFromMethodOf(id).invoke(null, in);
    }
}
