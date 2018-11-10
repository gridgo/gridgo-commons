package io.gridgo.bean;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import io.gridgo.bean.serialize.BSerializerAware;

public interface BElement extends BSerializerAware {

	void writeString(String name, int numTab, StringBuilder writer);

	BType getType();

	String toJson();

	<T> T toJsonElement();

	String toXml(String name);

	<T> T deepClone();

	static <T extends BElement> T fromAny(Object data) {
		return BFactory.DEFAULT.fromAny(data);
	}

	static <T extends BElement> T fromXml(String xml) {
		return BFactory.DEFAULT.fromXml(xml);
	}

	static <T extends BElement> T fromJson(String json) {
		return BFactory.DEFAULT.fromJson(json);
	}

	static <T extends BElement> T fromRaw(InputStream in) {
		return BFactory.DEFAULT.fromRaw(in);
	}

	static <T extends BElement> T fromRaw(ByteBuffer buffer) {
		return BFactory.DEFAULT.fromRaw(buffer);
	}

	static <T extends BElement> T fromRaw(byte[] bytes) {
		return BFactory.DEFAULT.fromRaw(bytes);
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

	default boolean isArray() {
		return this instanceof BArray;
	}

	default boolean isObject() {
		return this instanceof BObject;
	}

	default boolean isValue() {
		return this instanceof BValue;
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
}
