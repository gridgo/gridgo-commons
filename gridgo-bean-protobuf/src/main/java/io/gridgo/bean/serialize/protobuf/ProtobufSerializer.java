package io.gridgo.bean.serialize.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.MessageLite;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BReference;
import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.serialization.binary.AbstractBSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;
import lombok.Getter;

@BSerializationPlugin(name = "protobuf")
public class ProtobufSerializer extends AbstractBSerializer {

    @Getter
    private final ExtensionRegistry extensionRegistry = ExtensionRegistry.newInstance();

    @Override
    public void serialize(BElement element, OutputStream out) {
        if (!(element instanceof BReference)) {
            throw new BeanSerializationException("Protobuf serializer support only BReference");
        }
        if (!(element.asReference().getReference() instanceof MessageLite)) {
            throw new BeanSerializationException("Reference object must be instanceof " + MessageLite.class.getName());
        }
        MessageLite msgLiteObj = element.asReference().getReference();
        try {
            msgLiteObj.writeTo(out);
        } catch (IOException e) {
            throw new BeanSerializationException("Cannot write protobuf message to output stream", e);
        }
    }

    @Override
    public BElement deserialize(InputStream in) {
        return null;
    }
}
