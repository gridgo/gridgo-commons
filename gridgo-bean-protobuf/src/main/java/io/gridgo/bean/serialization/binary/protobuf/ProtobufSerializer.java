package io.gridgo.bean.serialization.binary.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BReference;
import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.SchemaInvalidException;
import io.gridgo.bean.serialization.binary.AbstractHasSchemaSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;
import io.gridgo.utils.ByteArrayUtils;
import io.gridgo.utils.PrimitiveUtils;

@BSerializationPlugin(ProtobufSerializer.NAME)
public class ProtobufSerializer extends AbstractHasSchemaSerializer {

    public static final String NAME = "protobuf";

    private final Map<Integer, Method> parseFromMethodCache = new NonBlockingHashMap<>();

    public ProtobufSerializer() {
        super(MessageLite.class);
    }

    @Override
    protected void onRegisterSchema(Class<?> schema, int id) {
        this.parseFromMethodCache.putIfAbsent(id, extractParseFromMethod(schema));
    }

    @Override
    protected void onDeregisterSchema(Class<?> schema, int id) {
        this.parseFromMethodCache.remove(id);
    }

    private Method getParseFromMethodOf(int id) throws Exception {
        return parseFromMethodCache.get(id);
    }

    private Method extractParseFromMethod(Class<?> schema) {
        try {
            return schema.getMethod("parseFrom", InputStream.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new SchemaInvalidException("Schema doesn't contain 'parseFrom' method or method isn't accessible");
        }
    }

    @Override
    public void serialize(BElement element, OutputStream out) {
        if (!(element instanceof BReference)) {
            throw new BeanSerializationException("Protobuf serializer support only BReference");
        }
        if (!(element.asReference().getReference() instanceof MessageLite)) {
            throw new BeanSerializationException("Reference object must be instanceof " + MessageLite.class.getName());
        }
        MessageLite msgLiteObj = element.asReference().getReference();
        Integer id = this.lookupId(msgLiteObj.getClass());
        if (id == null) {
            throw new SchemaInvalidException("Schema " + msgLiteObj.getClass() + " wasn't registered");
        }
        try {
            out.write(ByteArrayUtils.primitiveToBytes(id));
            msgLiteObj.writeTo(out);
        } catch (IOException e) {
            throw new BeanSerializationException("Cannot write protobuf message to output stream", e);
        }
    }

    @Override
    public BElement deserialize(InputStream in) {
        try {
            byte[] idBytes = new byte[4];
            in.read(idBytes);
            var id = PrimitiveUtils.getIntegerValueFrom(idBytes);
            var obj = getParseFromMethodOf(id).invoke(null, in);
            return this.getFactory().fromAny(obj);
        } catch (Exception e) {
            throw new BeanSerializationException("Error while reading input stream", e);
        }
    }
}
