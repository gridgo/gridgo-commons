package io.gridgo.bean.impl;

import java.util.List;

import io.gridgo.bean.BElement;

@SuppressWarnings("unchecked")
class DefaultBArray extends AbstractBArray {

    DefaultBArray(List<BElement> source) {
        super(source);
    }
}