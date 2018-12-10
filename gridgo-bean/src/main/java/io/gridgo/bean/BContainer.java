package io.gridgo.bean;

public interface BContainer extends BElement, BFactoryAware {

    int size();

    boolean isEmpty();

    void clear();
}
