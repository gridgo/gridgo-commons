package io.gridgo.bean.serialization.binary.avro;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.specific.SpecificRecord;

import io.gridgo.bean.exceptions.SchemaInvalidException;
import io.gridgo.bean.serialization.binary.AbstractHasSchemaSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;
import lombok.NonNull;

@BSerializationPlugin(AvroSerializer.NAME)
public class AvroSerializer extends AbstractHasSchemaSerializer<SpecificRecord> {

    public static final String NAME = "avro";

    private final Map<Integer, BinaryMessageDecoder<SpecificRecord>> decoderCache = new HashMap<>();
    private final Map<Integer, BinaryMessageEncoder<SpecificRecord>> encoderCache = new HashMap<>();

    public AvroSerializer() {
        super(SpecificRecord.class);
    }

    @Override
    protected void onSchemaRegistered(Class<? extends SpecificRecord> schema, int id) {
        this.decoderCache.putIfAbsent(id, extractDecoder(schema));
        this.encoderCache.putIfAbsent(id, extractEncoder(schema));
    }

    @Override
    protected void onSchemaDeregistered(Class<? extends SpecificRecord> schema, int id) {
        this.decoderCache.remove(id);
        this.encoderCache.remove(id);
    }

    private BinaryMessageDecoder<SpecificRecord> getDecoderOf(int id) throws Exception {
        return decoderCache.get(id);
    }

    private BinaryMessageEncoder<SpecificRecord> getEncoderOf(int id) throws Exception {
        return encoderCache.get(id);
    }

    @SuppressWarnings("unchecked")
    private <T> T extractStaticField(@NonNull Class<?> clazz, @NonNull String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(null);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            throw new SchemaInvalidException("Schema doesn't contain '" + fieldName + "' field or field isn't accessible", e);
        }
    }

    private BinaryMessageEncoder<SpecificRecord> extractEncoder(Class<?> schema) {
        return extractStaticField(schema, "ENCODER");
    }

    private BinaryMessageDecoder<SpecificRecord> extractDecoder(Class<?> schema) {
        return extractStaticField(schema, "DECODER");
    }

    @Override
    protected void doSerialize(int id, SpecificRecord msgObj, OutputStream out) throws Exception {
        getEncoderOf(id).encode(msgObj, out);
    }

    @Override
    protected Object doDeserialize(InputStream in, int id) throws Exception {
        return getDecoderOf(id).decode(in);
    }
}
