package io.gridgo.bean.test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Foo {

    public Foo() {

    }

    private Foo(int i, double d, String s, Bar b) {
        this.i = i;
        this.d = d;
        this.s = s;
        this.b = b;
    }

    private int i;

    private double d;

    private String s;

    private Bar b;
}
