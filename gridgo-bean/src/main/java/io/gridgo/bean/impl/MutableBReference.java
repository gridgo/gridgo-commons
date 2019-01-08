package io.gridgo.bean.impl;

import io.gridgo.bean.BReference;
import io.gridgo.bean.serialize.BSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MutableBReference implements BReference {

    @Setter
    @Getter
    private Object reference;

    @Setter
    @Getter
    private transient BSerializer serializer;

    @Override
    public String toString() {
        return "reference-to-instanceOf:" + (reference == null ? null : reference.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        final Object myValue = this.getReference();
        final Object other;
        if (obj instanceof BReference) {
            other = ((BReference) obj).getReference();
        } else {
            other = obj;
        }
        return myValue == null //
                ? other == null //
                : myValue.equals(obj);
    }

    @Override
    public int hashCode() {
        return reference != null ? reference.hashCode() : super.hashCode();
    }
}