package io.gridgo.bean.serialization.binary.protobuf;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.exceptions.SchemaInvalidException;
import io.gridgo.bean.serialization.binary.AbstractHasSchemaSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;

@BSerializationPlugin(ProtobufSerializer.NAME)
public class ProtobufSerializer extends AbstractHasSchemaSerializer<MessageLite> {

    public static final String NAME = "protobuf";

    private final Map<Integer, Method> parseFromMethodCache = new NonBlockingHashMap<>();

    public ProtobufSerializer() {
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

    private Method extractParseFromMethod(Class<? extends MessageLite> schema) {
        try {
            return schema.getMethod("parseFrom", InputStream.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new SchemaInvalidException("Schema doesn't contain 'parseFrom' method or method isn't accessible");
        }
    }

    @Override
    public void doSerialize(int id, MessageLite msgObj, OutputStream out) throws Exception {
        msgObj.writeTo(out);
    }

    @Override
    public Object doDeserialize(InputStream in, int id) throws Exception {
        return getParseFromMethodOf(id).invoke(null, in);
    }
}
