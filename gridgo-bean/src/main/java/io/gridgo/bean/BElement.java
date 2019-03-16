package io.gridgo.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.function.Function;

import io.gridgo.bean.factory.BFactory;
import io.gridgo.bean.serialization.binary.BSerializerRegistryAware;
import lombok.NonNull;

public interface BElement extends BSerializerRegistryAware {

    void writeString(String name, int numTab, StringBuilder writer);

    BType getType();

    String toJson();

    void writeJson(Appendable out);

    default void writeJson(OutputStream out) {
        try (var outWriter = new OutputStreamWriter(out)) {
            this.writeJson(outWriter);
        } catch (IOException e) {
            throw new RuntimeException("Cannot close output writer after write json", e);
        }
    }

    <T> T toJsonElement();

    String toXml(String name);

    <T> T deepClone();

    static <T extends BElement> T wrapAny(Object data) {
        return BFactory.DEFAULT.wrap(data);
    }

    static <T extends BElement> T ofAny(Object data) {
        return BFactory.DEFAULT.fromAny(data);
    }

    static <T extends BElement> T ofXml(String xml) {
        return BFactory.DEFAULT.fromXml(xml);
    }

    static <T extends BElement> T ofJson(String json) {
        return BFactory.DEFAULT.fromJson(json);
    }

    static <T extends BElement> T ofJson(Reader reader) {
        return BFactory.DEFAULT.fromJson(reader);
    }

    static <T extends BElement> T ofJson(InputStream inputStream) {
        return BFactory.DEFAULT.fromJson(inputStream);
    }

    static <T extends BElement> T ofBytes(@NonNull InputStream in, String serializerName) {
        return BFactory.DEFAULT.fromBytes(in, serializerName);
    }

    static <T extends BElement> T ofBytes(@NonNull ByteBuffer buffer, String serializerName) {
        return BFactory.DEFAULT.fromBytes(buffer, serializerName);
    }

    static <T extends BElement> T ofBytes(@NonNull byte[] bytes, String serializerName) {
        return BFactory.DEFAULT.fromBytes(bytes, serializerName);
    }

    static <T extends BElement> T ofBytes(@NonNull InputStream in) {
        return ofBytes(in, null);
    }

    static <T extends BElement> T ofBytes(@NonNull ByteBuffer buffer) {
        return ofBytes(buffer, null);
    }

    static <T extends BElement> T ofBytes(@NonNull byte[] bytes) {
        return ofBytes(bytes, null);
    }

    default void writeBytes(ByteBuffer buffer) {
        this.getSerializer().serialize(this, buffer);
    }

    default void writeBytes(OutputStream out) {
        this.getSerializer().serialize(this, out);
    }

    default byte[] toBytes(int initCapacity) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(initCapacity);
        this.writeBytes(out);
        return out.toByteArray();
    }

    default byte[] toBytes() {
        return this.toBytes(this.getSerializer().getMinimumOutputCapactity());
    }

    default String toXml() {
        return this.toXml(null);
    }

    default boolean isContainer() {
        return this instanceof BContainer;
    }

    default boolean isNullValue() {
        return this.isValue() && this.asValue().isNull();
    }

    default boolean isArray() {
        return this instanceof BArray;
    }

    default <T> T isArrayThen(Function<BArray, T> handler) {
        if (this.isArray()) {
            return handler.apply(this.asArray());
        }
        return null;
    }

    default void isArrayThen(Consumer<BArray> handler) {
        if (this.isArray()) {
            handler.accept(this.asArray());
        }
    }

    default boolean isObject() {
        return this instanceof BObject;
    }

    default <T> T isObjectThen(Function<BObject, T> handler) {
        if (this.isObject()) {
            return handler.apply(this.asObject());
        }
        return null;
    }

    default void isObjectThen(Consumer<BObject> handler) {
        if (this.isObject()) {
            handler.accept(this.asObject());
        }
    }

    default boolean isValue() {
        return this instanceof BValue;
    }

    default <T> T isValueThen(Function<BValue, T> handler) {
        if (this.isValue()) {
            return handler.apply(this.asValue());
        }
        return null;
    }

    default void isValueThen(Consumer<BValue> handler) {
        if (this.isValue()) {
            handler.accept(this.asValue());
        }
    }

    default boolean isReference() {
        return this instanceof BReference;
    }

    default <T> T isReferenceThen(Function<BReference, T> handler) {
        if (this.isReference()) {
            return handler.apply(this.asReference());
        }
        return null;
    }

    default void isReferenceThen(Consumer<BReference> handler) {
        if (this.isReference()) {
            handler.accept(this.asReference());
        }
    }

    default BObject asObject() {
        return (BObject) this;
    }

    default BArray asArray() {
        return (BArray) this;
    }

    default BValue asValue() {
        return (BValue) this;
    }

    default BReference asReference() {
        return (BReference) this;
    }
}
