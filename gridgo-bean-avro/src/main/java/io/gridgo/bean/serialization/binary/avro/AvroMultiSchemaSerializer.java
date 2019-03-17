package io.gridgo.bean.serialization.binary.avro;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.specific.SpecificRecord;

import io.gridgo.bean.serialization.binary.AbstractMultiSchemaSerializer;
import io.gridgo.bean.serialization.binary.BSerializationPlugin;
import lombok.NonNull;

@BSerializationPlugin(AvroMultiSchemaSerializer.NAME)
public class AvroMultiSchemaSerializer extends AbstractMultiSchemaSerializer<SpecificRecord> implements AvroSerializer {

    public static final String NAME = "avroMultiSchema";

    private final Map<Integer, BinaryMessageDecoder<SpecificRecord>> decoderCache = new HashMap<>();
    private final Map<Integer, BinaryMessageEncoder<SpecificRecord>> encoderCache = new HashMap<>();

    public AvroMultiSchemaSerializer() {
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

    @Override
    protected void doSerialize(@NonNull Integer id, @NonNull SpecificRecord msgObj, OutputStream out) throws Exception {
        encoderCache.get(id).encode(msgObj, out);
    }

    @Override
    protected Object doDeserialize(@NonNull InputStream in, @NonNull Integer id) throws Exception {
        return decoderCache.get(id).decode(in);
    }
}
