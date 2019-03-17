package io.gridgo.bean;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.bean.serialization.BSerializer;
import io.gridgo.bean.serialization.BSerializerRegistryAware;
import lombok.NonNull;

public interface BBytesSupport extends BSerializerRegistryAware {

    default BSerializer lookupOrDefaultSerializer(String serializerName) {
        var serializer = this.getSerializerRegistry().lookupOrDefault(serializerName);
        if (serializer == null) {
            throw new BeanSerializationException("Serializer doesn't available for name: " + serializerName);
        }
        return serializer;
    }

    default void writeBytes(@NonNull ByteBuffer buffer, String serializerName) {
        if (this instanceof BElement)
            lookupOrDefaultSerializer(serializerName).serialize((BElement) this, buffer);
        else
            throw new InvalidTypeException("Cannot write bytes to output stream from not-a-BElement object");
    }

    default void writeBytes(@NonNull OutputStream out, String serializerName) {
        if (this instanceof BElement)
            lookupOrDefaultSerializer(serializerName).serialize((BElement) this, out);
        else
            throw new InvalidTypeException("Cannot write bytes to output stream from not-a-BElement object");
    }

    default byte[] toBytes(int initCapacity, String serializerName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(initCapacity);
        this.writeBytes(out, serializerName);
        return out.toByteArray();
    }

    default byte[] toBytes(String serializerName) {
        return this.toBytes(this.getSerializer().getMinimumOutputCapactity(), serializerName);
    }

    default void writeBytes(ByteBuffer buffer) {
        writeBytes(buffer, null);
    }

    default void writeBytes(OutputStream out) {
        writeBytes(out, null);
    }

    default byte[] toBytes(int initCapacity) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(initCapacity);
        this.writeBytes(out);
        return out.toByteArray();
    }

    default byte[] toBytes() {
        return this.toBytes(this.getSerializer().getMinimumOutputCapactity());
    }

}
