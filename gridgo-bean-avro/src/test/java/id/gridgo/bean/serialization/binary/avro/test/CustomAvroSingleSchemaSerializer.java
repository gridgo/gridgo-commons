package id.gridgo.bean.serialization.binary.avro.test;

import id.gridgo.bean.serialization.binary.avro.test.support.Person;
import io.gridgo.bean.serialization.BSerializationPlugin;
import io.gridgo.bean.serialization.avro.AvroSingleSchemaSerializer;

@BSerializationPlugin(CustomAvroSingleSchemaSerializer.NAME)
public class CustomAvroSingleSchemaSerializer extends AvroSingleSchemaSerializer {
    public static final String NAME = "customAvroSingleSchema";

    public CustomAvroSingleSchemaSerializer() {
        super();
        this.setSchema(Person.class);
    }
}
