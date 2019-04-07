package io.gridgo.bean.serialization.gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.bean.exceptions.BeanSerializationException;
import io.gridgo.bean.serialization.AbstractBSerializer;
import io.gridgo.bean.serialization.BSerializationPlugin;
import io.gridgo.utils.ByteArrayUtils;
import io.gridgo.utils.exception.RuntimeIOException;

@BSerializationPlugin(GsonSerialzier.NAME)
public class GsonSerialzier extends AbstractBSerializer {

    public static final String NAME = "gson";

    private void writeAny(JsonWriter writer, BElement element) throws IOException {
        if (element.isArray()) {
            writeArray(writer, element.asArray());
        } else if (element.isObject()) {
            writeObject(writer, element.asObject());
        } else {
            writeValue(writer, element.toJsonElement());
        }
    }

    private void writeObject(JsonWriter writer, BObject object) throws IOException {
        writer.beginObject();
        for (Entry<String, BElement> entry : object.entrySet()) {
            writer.name(entry.getKey());
            writeAny(writer, entry.getValue());
        }
        writer.endObject();
    }

    private void writeArray(JsonWriter writer, BArray array) throws IOException {
        writer.beginArray();
        for (BElement entry : array) {
            writeAny(writer, entry);
        }
        writer.endArray();
    }

    private void writeValue(JsonWriter writer, Object value) throws IOException {
        if (value == null) {
            writer.nullValue();
        } else if (value instanceof Long) {
            writer.value((Long) value);
        } else if (value instanceof Double) {
            writer.value((Double) value);
        } else if (value instanceof Number) {
            writer.value((Number) value);
        } else if (value instanceof Boolean) {
            writer.value((Boolean) value);
        } else if (value instanceof String) {
            writer.value((String) value);
        } else if (value instanceof byte[]) {
            writer.value(ByteArrayUtils.toHex((byte[]) value, "0x"));
        } else if (value instanceof Character) {
            writer.value(String.valueOf((Character) value));
        } else {
            throw new BeanSerializationException("Cannot write json from value instanceof " + value.getClass());
        }
    }

    @Override
    public void serialize(BElement element, OutputStream out) {
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(out));
        try {
            writeAny(jsonWriter, element);
            jsonWriter.flush();
        } catch (IOException e) {
            throw new RuntimeIOException("Error while writing out element", e);
        }
    }

    private BElement readAny(JsonElement element) {
        if (element.isJsonNull()) {
            return BValue.ofEmpty();
        } else if (element.isJsonArray()) {
            return readArray((JsonArray) element);
        } else if (element.isJsonObject()) {
            return readObject((JsonObject) element);
        } else if (element.isJsonPrimitive()) {
            return readValue((JsonPrimitive) element);
        }
        throw new BeanSerializationException("Cannot read JsonElement" + element);
    }

    private BElement readValue(JsonPrimitive element) {
        if (element.isBoolean()) {
            return BValue.of(element.getAsBoolean());
        } else if (element.isNumber()) {
            return BValue.of(element.getAsNumber());
        } else if (element.isString()) {
            return BValue.of(element.getAsString());
        } else {
            throw new BeanSerializationException("Unrecognized JsonPrimitive type" + element);
        }
    }

    private BElement readObject(JsonObject element) {
        BObject result = BObject.ofEmpty();
        for (Entry<String, JsonElement> entry : element.entrySet()) {
            result.put(entry.getKey(), readAny(entry.getValue()));
        }
        return result;
    }

    private BArray readArray(JsonArray array) {
        BArray result = BArray.ofEmpty();
        for (JsonElement element : array) {
            result.add(readAny(element));
        }
        return result;
    }

    @Override
    public BElement deserialize(InputStream in) {
        JsonElement element = new JsonParser().parse(new InputStreamReader(in));
        return readAny(element);
    }

}
