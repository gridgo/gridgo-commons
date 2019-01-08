package io.gridgo.bean;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public interface WrappedBObject extends BObject {

    Map<?, ?> getSource();

    @Override
    default int size() {
        return getSource().size();
    }

    @Override
    default boolean isEmpty() {
        return getSource().isEmpty();
    }

    @Override
    default boolean containsKey(Object key) {
        return getSource().containsKey(key);
    }

    @Override
    default boolean containsValue(Object value) {
        return getSource().containsValue(value);
    }

    @Override
    default BElement get(Object key) {
        return BElement.ofAny(key);
    }

    @Override
    default Set<String> keySet() {
        return getSource().keySet() //
                          .stream() //
                          .map(Object::toString) //
                          .collect(Collectors.toSet());
    }

    @Override
    default Collection<BElement> values() {
        return getSource().values() //
                          .stream() //
                          .map(obj -> (BElement) BElement.ofAny(obj)) //
                          .collect(Collectors.toList());
    }

    @Override
    default Set<Entry<String, BElement>> entrySet() {
        return getSource().entrySet() //
                          .stream() //
                          .collect(Collectors.toMap(Object::toString, val -> (BElement) BElement.ofAny(val))) //
                          .entrySet();
    }

    @Override
    default BElement getOrDefault(Object key, BElement defaultValue) {
        BElement v;
        return (((v = get(key)) != null) || containsKey(key)) ? v : defaultValue;
    }

    @Override
    default void forEach(BiConsumer<? super String, ? super BElement> action) {
        this.getSource().entrySet().forEach(entry -> {
            action.accept(entry.getKey().toString(), BElement.ofAny(entry.getValue()));
        });
    }
}
