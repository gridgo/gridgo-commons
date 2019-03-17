package io.gridgo.bean.serialization.protobuf;

import java.io.InputStream;
import java.lang.reflect.Method;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.exceptions.SchemaInvalidException;
import io.gridgo.bean.serialization.BSerializer;

public interface ProtobufSerializer extends BSerializer {

    default Method extractParseFromMethod(Class<? extends MessageLite> schema) {
        try {
            return schema.getMethod("parseFrom", InputStream.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new SchemaInvalidException("Schema doesn't contain 'parseFrom' method or method isn't accessible");
        }
    }
}
