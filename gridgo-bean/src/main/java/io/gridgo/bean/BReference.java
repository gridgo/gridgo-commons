package io.gridgo.bean;

import java.io.IOException;

import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.factory.BFactory;
import io.gridgo.utils.StringUtils;
import io.gridgo.utils.exception.RuntimeIOException;

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

    @Override
    public default void writeXml(Appendable out, String name) {
        var ref = getReference();
        try {
            if (name == null)
                out.append("<reference>");
            else
                out.append("<reference name=\"").append(name).append("\"/>");

            if (ref instanceof SerializableReference) {
                out.append(((SerializableReference) ref).toXml());
            } else {
                out.append("instanceOf:").append(ref == null ? "null" : ref.getClass().getName());
            }

            out.append("</reference>");
        } catch (IOException e) {
            throw new RuntimeIOException("Error while write out xml", e);
        }
    }

    public default String toXml(String name) {
        StringBuilder builder = new StringBuilder();
        this.writeXml(builder, name);
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    public default <T> T deepClone() {
        return (T) of(this.getReference());
    }
}
