package io.gridgo.bean.impl;

import java.util.HashMap;
import java.util.Map;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BFactory;
import io.gridgo.bean.BObject;
import io.gridgo.bean.serialize.BSerializer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@SuppressWarnings("unchecked")
class DefaultBObject extends HashMap<String, BElement> implements BObject {

    private static final long serialVersionUID = -782587140021900238L;

    @Setter
    @Getter
    private transient BFactory factory = BFactory.DEFAULT;

    @Setter
    @Getter
    private transient BSerializer serializer;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.writeString(null, 0, sb);
        return sb.toString();
    }

    @Override
    public BElement put(@NonNull String key, @NonNull BElement value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(@NonNull Map<? extends String, ? extends BElement> m) {
        Map<String, BElement> map = new HashMap<>();
        for (Entry<? extends String, ? extends BElement> entry : m.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        super.putAll(map);
    }

    @Override
    public BElement putIfAbsent(@NonNull String key, @NonNull BElement value) {
        return super.putIfAbsent(key, value);
    }
}
