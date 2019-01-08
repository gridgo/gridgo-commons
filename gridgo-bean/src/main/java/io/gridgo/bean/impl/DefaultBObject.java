package io.gridgo.bean.impl;

import java.util.Map;

import io.gridgo.bean.BElement;

@SuppressWarnings("unchecked")
class DefaultBObject extends AbstractBObject {

    DefaultBObject(Map<String, BElement> source) {
        super(source);
    }
}
