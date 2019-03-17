package io.gridgo.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public interface BXmlSupport {

    String toXml(String name);

    void writeXml(Appendable out, String name);

    default void writeXml(OutputStream out, String name) {
        try (var outWriter = new OutputStreamWriter(out)) {
            this.writeXml(outWriter, name);
        } catch (IOException e) {
            throw new RuntimeException("Cannot close output writer after write xml", e);
        }
    }

    default String toXml() {
        return this.toXml(null);
    }
}
