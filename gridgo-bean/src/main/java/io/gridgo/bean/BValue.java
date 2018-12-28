package io.gridgo.bean;

import java.io.IOException;
import java.util.Base64;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.exceptions.InvalidTypeException;
import io.gridgo.utils.ByteArrayUtils;
import io.gridgo.utils.PrimitiveUtils;
import io.gridgo.utils.StringUtils;

public interface BValue extends BElement {

    static BValue ofEmpty() {
        return BFactory.DEFAULT.newValue();
    }

    static BValue of(Object data) {
        return BFactory.DEFAULT.newValue(data);
    }

    @Override
    default BType getType() {
        if (!this.isNull()) {
            if (this.getData() instanceof Boolean) {
                return BType.BOOLEAN;
            } else if (this.getData() instanceof Character) {
                return BType.CHAR;
            } else if (this.getData() instanceof Byte) {
                return BType.BYTE;
            } else if (this.getData() instanceof Short) {
                return BType.SHORT;
            } else if (this.getData() instanceof Integer) {
                return BType.INTEGER;
            } else if (this.getData() instanceof Float) {
                return BType.FLOAT;
            } else if (this.getData() instanceof Long) {
                return BType.LONG;
            } else if (this.getData() instanceof Double) {
                return BType.DOUBLE;
            } else if (this.getData() instanceof String) {
                return BType.STRING;
            } else if (this.getData() instanceof byte[]) {
                return BType.RAW;
            }
            throw new InvalidTypeException("Cannot recognize data type: " + this.getData().getClass());
        }
        return BType.NULL;
    }

    void setData(Object data);

    Object getData();

    default boolean isNull() {
        return this.getData() == null;
    }

    default Boolean getBoolean() {
        if (!this.isNull()) {
            return PrimitiveUtils.getBooleanValueFrom(this.getData());
        }
        return null;
    }

    default Character getChar() {
        if (!this.isNull()) {
            return PrimitiveUtils.getCharValueFrom(this.getData());
        }
        return null;
    }

    default Byte getByte() {
        if (!this.isNull()) {
            return PrimitiveUtils.getByteValueFrom(this.getData());
        }
        return null;
    }

    default Short getShort() {
        if (!this.isNull()) {
            return PrimitiveUtils.getShortValueFrom(this.getData());
        }
        return null;
    }

    default Integer getInteger() {
        if (!this.isNull()) {
            return PrimitiveUtils.getIntegerValueFrom(this.getData());
        }
        return null;
    }

    default Float getFloat() {
        if (!this.isNull()) {
            return PrimitiveUtils.getFloatValueFrom(this.getData());
        }
        return null;
    }

    default Long getLong() {
        if (!this.isNull()) {
            return PrimitiveUtils.getLongValueFrom(this.getData());
        }
        return null;
    }

    default Double getDouble() {
        if (!this.isNull()) {
            return PrimitiveUtils.getDoubleValueFrom(this.getData());
        }
        return null;
    }

    default String getString() {
        if (!this.isNull()) {
            return PrimitiveUtils.getStringValueFrom(this.getData());
        }
        return null;
    }

    default byte[] getRaw() {
        if (!this.isNull()) {
            return ByteArrayUtils.primitiveToBytes(this.getData());
        }
        return null;
    }

    @Override
    default String toJson() {
        if (!this.isNull()) {
            if (this.getData() instanceof byte[]) {
                return ByteArrayUtils.toHex(this.getRaw(), "0x");
            }
            return this.getString();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object toJsonElement() {
        if (!this.isNull()) {
            if (this.getData() instanceof byte[]) {
                return ByteArrayUtils.toHex(this.getRaw(), "0x");
            } else if (this.getData() instanceof Character) {
                return new String(new char[] { this.getChar() });
            }
            return this.getData();
        }
        return null;
    }

    @Override
    default void writeJson(Appendable out) {
        try {
            out.append(this.toJsonElement().toString());
        } catch (IOException e) {
            throw new BeanSerializationException("Error while writing json", e);
        }
    }

    @Override
    default String toXml(String name) {
        if (!this.isNull()) {
            String type = this.getType().name().toLowerCase();
            StringBuilder sb = new StringBuilder();
            sb.append("<").append(type);
            if (name != null) {
                sb.append(" name=\"").append(name).append("\"");
            }
            String content = this.getData() instanceof byte[] ? ByteArrayUtils.toHex(this.getRaw()) : this.getString();
            if (content.contains("<")) {
                sb.append(">") //
                  .append("<![CDATA[")//
                  .append(content) //
                  .append("]]>") //
                  .append("</").append(type).append(">");
            } else if (content.contains("\"")) {
                sb.append(">") //
                  .append(content) //
                  .append("</").append(type).append(">");
            } else {
                sb.append(" value=\"").append(content.replaceAll("\"", "\\\"")).append("\"/>");
            }
            return sb.toString();
        }
        return name == null ? "<null />" : ("<null name=\"" + name + "\"/>");
    }

    default void encodeHex() {
        if (!(this.getData() instanceof byte[])) {
            throw new InvalidTypeException("Cannot encode hex from data which is not in raw (byte[]) format");
        }
        this.setData(ByteArrayUtils.toHex(getRaw(), "0x"));
    }

    default void decodeHex() {
        if (this.getData() instanceof byte[]) {
            // skip decode if data already in byte[]
            return;
        }
        if (!(this.getData() instanceof String)) {
            throw new InvalidTypeException("Cannot decode hex from data which is not in String format");
        }
        String hex = this.getString();
        this.setData(ByteArrayUtils.fromHex(hex));
    }

    default void encodeBase64() {
        if (!(this.getData() instanceof byte[])) {
            throw new InvalidTypeException("Cannot encode base64 from data which is not in raw (byte[]) format");
        }
        this.setData(Base64.getEncoder().encodeToString(getRaw()));
    }

    default void decodeBase64() {
        if (!(this.getData() instanceof String)) {
            throw new InvalidTypeException("Cannot decode base64 from data which is not in String format");
        }
        String base64 = this.getString();
        this.setData(Base64.getDecoder().decode(base64));
    }

    default <T> T getDataAs(Class<T> targetType) {
        return PrimitiveUtils.getValueFrom(targetType, this.getData());
    }

    default BValue convertToBoolean() {
        this.setData(this.getBoolean());
        return this;
    }

    default BValue convertToChar() {
        this.setData(this.getChar());
        return this;
    }

    default BValue convertToByte() {
        this.setData(this.getByte());
        return this;
    }

    default BValue convertToShort() {
        this.setData(this.getShort());
        return this;
    }

    default BValue convertToInteger() {
        this.setData(this.getInteger());
        return this;
    }

    default BValue convertToLong() {
        this.setData(this.getLong());
        return this;
    }

    default BValue convertToFloat() {
        this.setData(this.getFloat());
        return this;
    }

    default BValue convertToDouble() {
        this.setData(this.getDouble());
        return this;
    }

    default BValue convertToRaw() {
        this.setData(this.getRaw());
        return this;
    }

    default BValue convertToString() {
        this.setData(this.getString());
        return this;
    }

    @Override
    default void writeString(String name, int numTab, StringBuilder writer) {
        StringUtils.tabs(numTab, writer);
        BType type = this.getType();
        String content = this.getString();
        if (name == null) {
            writer.append("(").append(type.name()).append(")");
        } else {
            writer.append(name).append(": ").append(type.name());
        }
        if (!this.isNull()) {
            writer.append(" = ").append(content);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    default <T> T deepClone() {
        return (T) of(this.getData());
    }
}
