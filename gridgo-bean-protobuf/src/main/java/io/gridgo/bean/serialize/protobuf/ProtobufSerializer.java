package io.gridgo.bean.serialize.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import com.google.protobuf.MessageLite;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BReference;
import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.serialization.binary.AbstractBSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;
import io.gridgo.utils.PrimitiveUtils;

@BSerializationPlugin(name = "protobuf")
public class ProtobufSerializer extends AbstractBSerializer {

    private final Map<Class<? extends MessageLite>, Integer> classToId = new NonBlockingHashMap<>();
    private final Map<Integer, Class<? extends MessageLite>> idToClass = new NonBlockingHashMap<>();

    @Override
    public void serialize(BElement element, OutputStream out) {
        if (!(element instanceof BReference)) {
            throw new BeanSerializationException("Protobuf serializer support only BReference");
        }
        if (!(element.asReference().getReference() instanceof MessageLite)) {
            throw new BeanSerializationException("Reference object must be instanceof " + MessageLite.class.getName());
        }
        MessageLite msgLiteObj = element.asReference().getReference();
        Integer id = this.classToId.get(msgLiteObj.getClass());
        if (id == null) {
            throw new BeanSerializationException("Class " + msgLiteObj.getClass() + " wasn't registered");
        }
        try {
            out.write(id.intValue());
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
            Class<? extends MessageLite> clazz = this.idToClass.get(id);
            var parseFromMethod = clazz.getMethod("parseFrom", InputStream.class);
            var obj = parseFromMethod.invoke(null, in);
            return this.getFactory().fromAny(obj);
        } catch (Exception e) {
            throw new BeanSerializationException("Error while reading input stream", e);
        }
    }
}
