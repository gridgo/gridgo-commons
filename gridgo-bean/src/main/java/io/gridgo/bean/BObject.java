package io.gridgo.bean;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.FieldNotFoundException;
import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.utils.ObjectUtils;
import io.gridgo.utils.StringUtils;
import net.minidev.json.JSONObject;

public interface BObject extends BContainer, Map<String, BElement> {

    default <T> T toPojo(Class<T> clazz) {
        try {
            return ObjectUtils.fromMap(clazz, this.toMap());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
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
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getBoolean();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as boolean");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Boolean getBoolean(String field, Boolean defaultValue) {
        if (this.containsKey(field)) {
            return this.getBoolean(field);
        }
        return defaultValue;
    }

    default Boolean getBoolean(String field, boolean defaultValue) {
        if (this.containsKey(field)) {
            return this.getBoolean(field);
        }
        return defaultValue;
    }

    default Character getChar(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getChar();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as char");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Character getChar(String field, Character defaultValue) {
        if (this.containsKey(field)) {
            return this.getChar(field);
        }
        return defaultValue;
    }

    default Character getChar(String field, char defaultValue) {
        if (this.containsKey(field)) {
            return this.getChar(field);
        }
        return defaultValue;
    }

    default Byte getByte(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getByte();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as char");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Byte getByte(String field, Byte defaultValue) {
        if (this.containsKey(field)) {
            return this.getByte(field);
        }
        return defaultValue;
    }

    default Byte getByte(String field, byte defaultValue) {
        if (this.containsKey(field)) {
            return this.getByte(field);
        }
        return defaultValue;
    }

    default Short getShort(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getShort();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as short");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Short getShort(String field, Short defaultValue) {
        if (this.containsKey(field)) {
            return this.getShort(field);
        }
        return defaultValue;
    }

    default Short getShort(String field, short defaultValue) {
        if (this.containsKey(field)) {
            return this.getShort(field);
        }
        return defaultValue;
    }

    default Integer getInteger(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getInteger();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as integer");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Integer getInteger(String field, Integer defaultValue) {
        if (this.containsKey(field)) {
            return this.getInteger(field);
        }
        return defaultValue;
    }

    default Integer getInteger(String field, int defaultValue) {
        if (this.containsKey(field)) {
            return this.getInteger(field);
        }
        return defaultValue;
    }

    default Float getFloat(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getFloat();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as float");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Float getFloat(String field, Float defaultValue) {
        if (this.containsKey(field)) {
            return this.getFloat(field);
        }
        return defaultValue;
    }

    default Float getFloat(String field, float defaultValue) {
        if (this.containsKey(field)) {
            return this.getFloat(field);
        }
        return defaultValue;
    }

    default Long getLong(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getLong();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as long");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Long getLong(String field, Long defaultValue) {
        if (this.containsKey(field)) {
            return this.getLong(field);
        }
        return defaultValue;
    }

    default Long getLong(String field, long defaultValue) {
        if (this.containsKey(field)) {
            return this.getLong(field);
        }
        return defaultValue;
    }

    default Double getDouble(String field) {
        if (this.containsKey(field)) {
            BElement element = this.get(field);
            if (element instanceof BValue) {
                return ((BValue) element).getDouble();
            }
            throw new InvalidTypeException(
                    "BObject contains element with type " + element.getType() + " which cannot get as double");
        }
        throw new FieldNotFoundException("Field not found: " + field);
    }

    default Double getDouble(String field, Double defaultValue) {
        if (this.containsKey(field)) {
            return this.getDouble(field);
        }
        return defaultValue;
    }

    default Double getDouble(String field, double defaultValue) {
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
        throw new FieldNotFoundException("Field not found: " + field);
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
