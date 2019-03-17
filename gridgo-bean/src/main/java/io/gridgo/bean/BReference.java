package io.gridgo.bean;

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

    @SuppressWarnings("unchecked")
    public default <T extends BElement> T deepClone() {
        return (T) of(this.getReference());
    }

    @Override
    @SuppressWarnings("unchecked")
    default String toJsonElement() {
        Object ref = this.getReference();
        String info = ref == null ? "null" : ref.getClass().getName();
        return new StringBuilder().append("instanceOf:").append(info).toString();
    }
}
