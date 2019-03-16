package io.gridgo.bean.serialization.binary.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BReference;
import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.SchemaInvalidException;
import io.gridgo.bean.serialization.binary.AbstractHasSchemaSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;
import io.gridgo.utils.ByteArrayUtils;
import io.gridgo.utils.PrimitiveUtils;

@BSerializationPlugin(name = "protobuf")
public class ProtobufSerializer extends AbstractHasSchemaSerializer {

    public ProtobufSerializer() {
        super(MessageLite.class);
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
            Class<?> clazz = lookupSchema(id);
            if (clazz == null) {
                throw new SchemaInvalidException("Cannot find schema for id: " + id);
            }
            var parseFromMethod = clazz.getMethod("parseFrom", InputStream.class);
            var obj = parseFromMethod.invoke(null, in);
            return this.getFactory().fromAny(obj);
        } catch (Exception e) {
            throw new BeanSerializationException("Error while reading input stream", e);
        }
    }
}
