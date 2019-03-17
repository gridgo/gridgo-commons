package io.gridgo.bean.serialization.avro;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.specific.SpecificRecord;

import io.gridgo.bean.serialization.AbstractSingleSchemaSerializer;
import io.gridgo.bean.serialization.BSerializationPlugin;

@BSerializationPlugin(AvroSingleSchemaSerializer.NAME)
public class AvroSingleSchemaSerializer extends AbstractSingleSchemaSerializer<SpecificRecord> implements AvroSerializer {

    public static final String NAME = "avroSingleSchema";

    private BinaryMessageDecoder<SpecificRecord> decoder;
    private BinaryMessageEncoder<SpecificRecord> encoder;

    @Override
    public void setSchema(Class<? extends SpecificRecord> schema) {
        super.setSchema(schema);
        this.decoder = extractDecoder(schema);
        this.encoder = extractEncoder(schema);
    }

    @Override
    protected void doSerialize(SpecificRecord msgObj, OutputStream out) throws Exception {
        encoder.encode(msgObj, out);
    }

    @Override
    protected Object doDeserialize(InputStream in) throws Exception {
        return this.decoder.decode(in);
    }
}
