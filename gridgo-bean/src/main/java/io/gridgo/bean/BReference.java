package io.gridgo.bean;

import java.io.IOException;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.factory.BFactory;
import io.gridgo.utils.StringUtils;

public interface BReference extends BElement {

    static BReference of(Object reference) {
        return BFactory.DEFAULT.newReference(reference);
    }

    @Override
    default BType getType() {
        return BType.REFERENCE;
    }

    public <T> T getReference();

    public void setReference(Object reference);

    public default void writeString(String name, int numTab, StringBuilder writer) {
        StringUtils.tabs(numTab, writer);
        BType type = this.getType();
        String content = "instanceOf:" + (this.getReference() == null ? "null" : this.getReference().getClass().getName());
        if (name == null) {
            writer.append("(").append(type.name()).append(")");
        } else {
            writer.append(name).append(": ").append(type.name());
        }
        writer.append(" = ").append(content);
    }

    @Override
    default void writeJson(Appendable out) {
        try {
            out.append(this.toJson());
        } catch (IOException e) {
            throw new BeanSerializationException("Error while writing json", e);
        }
    }

    public default String toJson() {
        var ref = getReference();
        if (ref instanceof SerializableReference)
            return ((SerializableReference) ref).toJson();
        return null;
    }

    @SuppressWarnings("unchecked")
    public default <T> T toJsonElement() {
        var ref = getReference();
        if (ref instanceof SerializableReference)
            return (T) ((SerializableReference) ref).toJsonElement();
        return null;
    }

    public default String toXml(String name) {
        var ref = getReference();
        if (ref instanceof SerializableReference)
            return ((SerializableReference) ref).toXml();
        return null;
    }

    @SuppressWarnings("unchecked")
    public default <T> T deepClone() {
        return (T) of(this.getReference());
    }
}
