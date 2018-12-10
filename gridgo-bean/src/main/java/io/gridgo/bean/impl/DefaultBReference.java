package io.gridgo.bean.impl;

import io.gridgo.bean.BReference;
import io.gridgo.bean.serialize.BSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
class DefaultBReference implements BReference {

    @Setter
    @Getter
    private Object reference;

    @Setter
    @Getter
    private transient BSerializer serializer;

    @Override
    public String toString() {
        return reference != null ? reference.toString() : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BReference) {
            var other = (BReference) obj;
            return reference == null //
                    ? other.getReference() == null //
                    : reference.equals(other.getReference());
        }
        return false;
    }
}