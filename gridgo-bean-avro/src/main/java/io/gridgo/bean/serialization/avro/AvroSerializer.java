package io.gridgo.bean.serialization.avro;

import java.lang.reflect.Field;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.specific.SpecificRecord;

import io.gridgo.bean.exceptions.SchemaInvalidException;
import io.gridgo.bean.serialization.BSerializer;
import lombok.NonNull;

public interface AvroSerializer extends BSerializer {

    @SuppressWarnings("unchecked")
    default <T> T extractStaticField(@NonNull Class<?> clazz, @NonNull String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(null);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            throw new SchemaInvalidException("Schema doesn't contain '" + fieldName + "' field or field isn't accessible", e);
        }
    }

    default BinaryMessageEncoder<SpecificRecord> extractEncoder(Class<?> schema) {
        return extractStaticField(schema, "ENCODER");
    }

    default BinaryMessageDecoder<SpecificRecord> extractDecoder(Class<?> schema) {
        return extractStaticField(schema, "DECODER");
    }
}
