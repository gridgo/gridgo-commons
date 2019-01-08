package io.gridgo.bean;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.utils.ObjectUtils;
import io.gridgo.utils.StringUtils;
import lombok.NonNull;
import net.minidev.json.JSONObject;

public interface BObject extends BContainer, Map<String, BElement> {

    default <T> T toPojo(Class<T> clazz) {
        try {
            return ObjectUtils.fromMap(clazz, this.toMap());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new BeanSerializationException("Exception caught while converting BObject to POJO", e);
        }
    }

    static BObject ofEmpty() {
        return BFactory.DEFAULT.newObject();
    }

    static BObject of(String name, Object value) {
        return ofEmpty().setAny(name, value);
    }

    static BObject of(Object data) {
        return BFactory.DEFAULT.newObject(data);
    }

    static BObject ofPojo(Object pojo) {
        BObject result = ofEmpty();
        result.putAnyAllPojo(pojo);
        return result;
    }

    static BObject ofPojoRecursive(Object pojo) {
        BObject result = ofEmpty();
        result.putAnyAllPojoRecursive(pojo);
        return result;
    }

    static BObject ofSequence(Object... sequence) {
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

    default Boolean getBoolean(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getBoolean();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as boolean");
        }
        return null;
    }

    default Boolean getBoolean(String field, Boolean defaultValue) {
        var value = this.getBoolean(field);
        return value == null ? defaultValue : value;
    }

    default Character getChar(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getChar();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Character");
        }
        return null;
    }

    default Character getChar(String field, Character defaultValue) {
        var value = this.getChar(field);
        return value == null ? defaultValue : value;
    }

    default Byte getByte(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getByte();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Byte");
        }
        return null;
    }

    default Byte getByte(String field, Number defaultValue) {
        var value = this.getByte(field);
        return value == null ? (defaultValue == null ? null : defaultValue.byteValue()) : value;
    }

    default Short getShort(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getShort();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Short");
        }
        return null;
    }

    default Short getShort(String field, Number defaultValue) {
        var value = this.getShort(field);
        return value == null ? (defaultValue == null ? null : defaultValue.shortValue()) : value;
    }

    default Integer getInteger(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getInteger();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Integer");
        }
        return null;
    }

    default Integer getInteger(String field, Number defaultValue) {
        var value = this.getInteger(field);
        return value == null ? (defaultValue == null ? null : defaultValue.intValue()) : value;
    }

    default Float getFloat(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getFloat();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Float");
        }
        return null;
    }

    default Float getFloat(String field, Number defaultValue) {
        var value = this.getFloat(field);
        return value == null ? (defaultValue == null ? null : defaultValue.floatValue()) : value;
    }

    default Long getLong(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getLong();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Long");
        }
        return null;
    }

    default Long getLong(String field, Number defaultValue) {
        var value = this.getLong(field);
        return value == null ? (defaultValue == null ? null : defaultValue.longValue()) : value;
    }

    default Double getDouble(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getDouble();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Double");
        }
        return null;
    }

    default Double getDouble(String field, Number defaultValue) {
        var value = this.getDouble(field);
        return value == null ? (defaultValue == null ? null : defaultValue.doubleValue()) : value;
    }

    default String getString(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getString();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as String");
        }
        return null;
    }

    default String getString(String field, String defaultValue) {
        var value = this.getString(field);
        return value == null ? defaultValue : value;
    }

    default byte[] getRaw(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isValue())
                return element.asValue().getRaw();

            throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as Raw");
        }
        return null;
    }

    default byte[] getRaw(String field, byte[] defaultValue) {
        var value = this.getRaw(field);
        return value == null ? defaultValue : value;
    }

    default BReference getReference(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isReference())
                return element.asReference();
            if (!element.isNullValue())
                throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as BReference");
        }
        return null;
    }

    default BReference getReference(String field, BReference defaultValue) {
        var value = this.getReference(field);
        return value == null ? defaultValue : value;
    }

    default BObject getObject(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isObject())
                return element.asObject();
            if (!element.isNullValue())
                throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as BObject");
        }
        return null;
    }

    default BObject getObject(String field, BObject defaultValue) {
        var value = this.getObject(field);
        return value == null ? defaultValue : value;
    }

    default BArray getArray(String field) {
        BElement element = this.get(field);
        if (element != null) {
            if (element.isArray())
                return element.asArray();
            if (!element.isNullValue())
                throw new InvalidTypeException("BObject contains element with type " + element.getType() + " which cannot get as BArray");
        }
        return null;
    }

    default BArray getArray(String field, BArray defaultValue) {
        var value = this.getArray(field);
        return value == null ? defaultValue : value;
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
            throw new BeanSerializationException("Writing json error", e);
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
            } else if (entry.getValue() instanceof BReference) {
                result.put(entry.getKey(), ((BReference) entry.getValue()).getReference());
            } else {
                if (entry.getValue() == null)
                    continue;
                throw new InvalidTypeException("Found unrecognized MElement implementation: " + entry.getValue().getClass());
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

    default BElement getOrDefault(String field, Supplier<BElement> supplierForNonPresent) {
        if (this.containsKey(field)) {
            return this.get(field);
        }
        return supplierForNonPresent.get();
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

    default BObject set(String name, @NonNull BElement value) {
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

    default BObjectOptional asOptional() {
        return new BObjectOptional() {

            @Override
            public BObject getBObject() {
                return BObject.this;
            }
        };
    }
}
