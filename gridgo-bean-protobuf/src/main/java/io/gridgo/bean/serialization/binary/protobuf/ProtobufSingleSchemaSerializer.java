package io.gridgo.bean.serialization.binary.protobuf;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.serialization.binary.AbstractSingleSchemaSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;

@BSerializationPlugin(ProtobufSingleSchemaSerializer.NAME)
public class ProtobufSingleSchemaSerializer extends AbstractSingleSchemaSerializer<MessageLite> implements ProtobufSerializer {

    public static final String NAME = "avroSingleSchema";

    private Method parseFrom;

    @Override
    public void setSchema(Class<? extends MessageLite> schema) {
        super.setSchema(schema);
        this.parseFrom = extractParseFromMethod(schema);
    }

    @Override
    protected void doSerialize(MessageLite msgObj, OutputStream out) throws Exception {
        msgObj.writeTo(out);
    }

    @Override
    protected Object doDeserialize(InputStream in) throws Exception {
        return this.parseFrom.invoke(null, in);
    }
}
