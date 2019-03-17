package io.gridgo.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public interface BJsonSupport {

    String toJson();

    void writeJson(Appendable out);

    default void writeJson(OutputStream out) {
        try (var outWriter = new OutputStreamWriter(out)) {
            this.writeJson(outWriter);
        } catch (IOException e) {
            throw new RuntimeException("Cannot close output writer after write json", e);
        }
    }

    <T> T toJsonElement();

}
