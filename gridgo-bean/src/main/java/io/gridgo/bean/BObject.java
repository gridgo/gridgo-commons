package io.gridgo.bean;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.utils.ObjectUtils;
import io.gridgo.utils.StringUtils;
import net.minidev.json.JSONObject;

public interface BObject extends BContainer, Map<String, BElement> {

	static BObject ofEmpty() {
		return BFactory.DEFAULT.newObject();
	}

	static BObject of(Object data) {
		return BFactory.DEFAULT.newObject(data);
	}

	static BObject newFromPojo(Object pojo) {
		BObject result = ofEmpty();
		result.putAnyAllPojo(pojo);
		return result;
	}

	static BObject newFromPojoRecursive(Object pojo) {
		BObject result = ofEmpty();
		result.putAnyAllPojoRecursive(pojo);
		return result;
	}

	static BObject newFromSequence(Object... sequence) {
		return BFactory.DEFAULT.newObjectFromSequence(sequence);
	}

	@Override
	default BType getType() {
		return BType.OBJECT;
	}

	default BType typeOf(String field) {
		if (this.containsKey(field)) {
			return this.get(field).getType();
		}
		return null;
	}

	default boolean getBoolean(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getBoolean();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as boolean");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default boolean getBoolean(String field, boolean defaultValue) {
		if (this.containsKey(field)) {
			return this.getBoolean(field);
		}
		return defaultValue;
	}

	default char getChar(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getChar();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as char");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default char getChar(String field, char defaultValue) {
		if (this.containsKey(field)) {
			return this.getChar(field);
		}
		return defaultValue;
	}

	default byte getByte(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getByte();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as char");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default byte getByte(String field, byte defaultValue) {
		if (this.containsKey(field)) {
			return this.getByte(field);
		}
		return defaultValue;
	}

	default short getShort(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getShort();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as short");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default short getShort(String field, short defaultValue) {
		if (this.containsKey(field)) {
			return this.getShort(field);
		}
		return defaultValue;
	}

	default int getInteger(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getInteger();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as integer");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default int getInteger(String field, int defaultValue) {
		if (this.containsKey(field)) {
			return this.getInteger(field);
		}
		return defaultValue;
	}

	default float getFloat(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getFloat();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as float");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default float getFloat(String field, float defaultValue) {
		if (this.containsKey(field)) {
			return this.getFloat(field);
		}
		return defaultValue;
	}

	default long getLong(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getLong();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as long");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default long getLong(String field, long defaultValue) {
		if (this.containsKey(field)) {
			return this.getLong(field);
		}
		return defaultValue;
	}

	default double getDouble(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getDouble();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as double");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default double getDouble(String field, double defaultValue) {
		if (this.containsKey(field)) {
			return this.getDouble(field);
		}
		return defaultValue;
	}

	default String getString(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getString();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as string");
		}
		throw new NullPointerException("Field not found: " + field);
	}

	default String getString(String field, String defaultValue) {
		if (this.containsKey(field)) {
			return this.getString(field);
		}
		return defaultValue;
	}

	default byte[] getRaw(String field) {
		if (this.containsKey(field)) {
			BElement element = this.get(field);
			if (element instanceof BValue) {
				return ((BValue) element).getRaw();
			}
			throw new InvalidTypeException(
					"BObject contains element with type " + element.getType() + " which cannot get as byte[]");
		}
		return null;
	}

	default byte[] getRaw(String field, byte[] defaultValue) {
		if (this.containsKey(field)) {
			return this.getRaw(field);
		}
		return defaultValue;
	}

	default BReference getReference(String field) {
		BType type = this.typeOf(field);
		if (type == BType.REFERENCE) {
			return (BReference) this.get(field);
		}
		throw new InvalidTypeException("Cannot get reference from field '" + field + "' which has type: " + type);
	}

	default BReference getReference(String field, BReference defaultValue) {
		if (this.containsKey(field)) {
			return getReference(field);
		}
		return defaultValue;
	}

	default BObject getObject(String field) {
		BType type = this.typeOf(field);
		if (type == BType.OBJECT) {
			return (BObject) this.get(field);
		}
		throw new InvalidTypeException("Cannot get object from field '" + field + "' which has type: " + type);
	}

	default BObject getObject(String field, BObject defaultValue) {
		if (this.containsKey(field)) {
			return this.getObject(field);
		}
		return defaultValue;
	}

	default BArray getArray(String field) {
		BType type = this.typeOf(field);
		if (type == BType.ARRAY) {
			return (BArray) this.get(field);
		}
		throw new InvalidTypeException("Cannot get array from field '" + field + "' which has type: " + type);
	}

	default BArray getArray(String field, BArray defaultValue) {
		if (this.containsKey(field)) {
			return this.getArray(field);
		}
		return defaultValue;
	}

	@Override
	@SuppressWarnings("unchecked")
	default Map<String, Object> toJsonElement() {
		Map<String, Object> map = new TreeMap<>();
		for (Entry<String, BElement> entry : this.entrySet()) {
			map.put(entry.getKey(), entry.getValue().toJsonElement());
		}
		return map;
	}

	@Override
	default void writeJson(Appendable out) {
		try {
			JSONObject.writeJSON(this.toJsonElement(), out);
		} catch (IOException e) {
			throw new RuntimeException("Writing json error", e);
		}
	}

	@Override
	default String toJson() {
		StringWriter out = new StringWriter();
		writeJson(out);
		return out.toString();
	}

	@Override
	default String toXml(String name) {
		StringBuilder builder = new StringBuilder();
		if (name == null) {
			builder.append("<object>");
		} else {
			builder.append("<object name=\"").append(name).append("\">");
		}
		for (Entry<String, BElement> entry : this.entrySet()) {
			builder.append(entry.getValue().toXml(entry.getKey()));
		}
		builder.append("</object>");
		return builder.toString();
	}

	default Map<String, Object> toMap() {
		Map<String, Object> result = new TreeMap<>();
		for (Entry<String, BElement> entry : this.entrySet()) {
			if (entry.getValue() instanceof BValue) {
				result.put(entry.getKey(), ((BValue) entry.getValue()).getData());
			} else if (entry.getValue() instanceof BArray) {
				result.put(entry.getKey(), ((BArray) entry.getValue()).toList());
			} else if (entry.getValue() instanceof BObject) {
				result.put(entry.getKey(), ((BObject) entry.getValue()).toMap());
			} else {
				throw new InvalidTypeException(
						"Found unrecognized MElement implementation: " + entry.getValue().getClass());
			}
		}
		return result;
	}

	default BElement putAny(String field, Object data) {
		return this.put(field, this.getFactory().fromAny(data));
	}

	default BElement putAnyIfAbsent(String field, Object data) {
		return this.putIfAbsent(field, this.getFactory().fromAny(data));
	}

	default void putAnyAll(Map<?, ?> map) {
		for (Entry<?, ?> entry : map.entrySet()) {
			this.putAny(entry.getKey().toString(), entry.getValue());
		}
	}

	default BElement putAnyPojo(String name, Object pojo) {
		return this.putAny(name, ObjectUtils.toMap(pojo));
	}

	default BElement putAnyPojoRecursive(String name, Object pojo) {
		return this.putAny(name, ObjectUtils.toMapRecursive(pojo));
	}

	default BElement putAnyPojoIfAbsent(String name, Object pojo) {
		return this.putAnyIfAbsent(name, ObjectUtils.toMap(pojo));
	}

	default BElement putAnyPojoRecursiveIfAbsent(String name, Object pojo) {
		return this.putAnyIfAbsent(name, ObjectUtils.toMapRecursive(pojo));
	}

	default void putAnyAllPojo(Object pojo) {
		this.putAnyAll(ObjectUtils.toMap(pojo));
	}

	default void putAnyAllPojoRecursive(Object pojo) {
		this.putAnyAll(ObjectUtils.toMapRecursive(pojo));
	}

	default void putAnySequence(Object... elements) {
		if (elements != null) {
			if (elements.length % 2 != 0) {
				throw new IllegalArgumentException("Sequence's length must be even");
			}
			for (int i = 0; i < elements.length - 1; i += 2) {
				this.putAny(elements[i].toString(), elements[i + 1]);
			}
		}
	}

	default BValue getValue(String field) {
		BElement element = this.get(field);
		return element != null ? element.asValue() : null;
	}

	@Override
	default void writeString(String name, int numTab, StringBuilder writer) {
		StringUtils.tabs(numTab, writer);
		if (name != null) {
			writer.append(name).append(": OBJECT = {");
		} else {
			writer.append("{");
		}
		writer.append(this.size() > 0 ? "\n" : "");
		int count = 0;
		for (Entry<String, BElement> entry : this.entrySet()) {
			entry.getValue().writeString(entry.getKey(), numTab + 1, writer);
			if (++count < this.size()) {
				writer.append(",\n");
			} else {
				writer.append("\n");
			}
		}
		if (this.size() > 0) {
			StringUtils.tabs(numTab, writer);
		}
		writer.append("}");
	}

	default BObject setAny(String name, Object value) {
		this.putAny(name, value);
		return this;
	}

	default BObject setAnyIfAbsent(String name, Object value) {
		this.putAnyIfAbsent(name, value);
		return this;
	}

	default BObject setAnyPojo(String name, Object pojo) {
		this.putAnyPojo(name, pojo);
		return this;
	}

	default BObject setAnyPojoRecursive(String name, Object pojo) {
		this.putAnyPojoRecursive(name, pojo);
		return this;
	}

	default BObject setAnyPojoIfAbsent(String name, Object pojo) {
		this.putAnyPojoIfAbsent(name, pojo);
		return this;
	}

	default BObject setAnyPojoRecursiveIfAbsent(String name, Object pojo) {
		this.putAnyPojoRecursiveIfAbsent(name, pojo);
		return this;
	}

	default BObject set(String name, BElement value) {
		this.put(name, value);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	default <T> T deepClone() {
		BObject result = ofEmpty();
		for (Entry<String, BElement> entry : this.entrySet()) {
			result.put(entry.getKey(), entry.getValue().deepClone());
		}
		return (T) result;
	}
}
