package io.gridgo.bean.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BElement;
import io.gridgo.bean.BFactory;
import io.gridgo.bean.serialize.BSerializer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@SuppressWarnings("unchecked")
class DefaultBArray extends ArrayList<BElement> implements BArray {

    private static final long serialVersionUID = 2037530547593981644L;

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
    public boolean add(@NonNull BElement e) {
        return super.add(e);
    }

    @Override
    public void add(int index, @NonNull BElement element) {
        super.add(index, element);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends BElement> c) {
        return super.addAll(c.stream().filter(ele -> ele != null).collect(Collectors.toList()));
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends BElement> c) {
        return super.addAll(index, c.stream().filter(ele -> ele != null).collect(Collectors.toList()));
    }
}