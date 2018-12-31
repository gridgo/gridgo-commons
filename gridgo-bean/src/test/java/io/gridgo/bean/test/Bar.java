package io.gridgo.bean.test;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Bar {

    public Bar() {

    }

    private Bar(boolean b, Map<String, Integer> map) {
        this.b = b;
        this.map = map;
    }

    private boolean b;

    private Map<String, Integer> map;
}
