package io.gridgo.bean.test.support;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Foo {

    private int i;

    private int[] arr;

    private double d;

    private String s;

    private Bar b;

    public Foo() {

    }

    private Foo(int i, int[] arr, double d, String s, Bar b) {
        this.i = i;
        this.arr = arr;
        this.d = d;
        this.s = s;
        this.b = b;
    }
}
