package io.gridgo.bean.serialization.text;

import java.io.InputStream;
import java.io.OutputStream;

import io.gridgo.bean.BElement;
import io.gridgo.bean.serialization.AbstractBSerializer;
import io.gridgo.bean.serialization.BSerializationPlugin;
import lombok.NonNull;

@BSerializationPlugin(JsonSerializer.NAME)
public class JsonSerializer extends AbstractBSerializer {

    public static final String NAME = "json";

    @Override
    public void serialize(@NonNull BElement element, @NonNull OutputStream out) {
        element.writeJson(out);
    }

    @Override
    public BElement deserialize(@NonNull InputStream in) {
        return BElement.ofJson(in);
    }

}
