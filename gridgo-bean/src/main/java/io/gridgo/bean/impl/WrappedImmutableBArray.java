package io.gridgo.bean.impl;

import java.util.List;

import io.gridgo.bean.ImmutableBArray;
import io.gridgo.bean.WrappedBArray;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@SuppressWarnings("unchecked")
class WrappedImmutableBArray extends AbstractBContainer implements WrappedBArray, ImmutableBArray {

    @Getter
    private final @NonNull List<?> source;
}
