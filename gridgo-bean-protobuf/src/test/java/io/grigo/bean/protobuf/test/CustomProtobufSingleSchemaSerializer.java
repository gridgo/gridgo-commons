package io.grigo.bean.protobuf.test;

import io.gridgo.bean.serialization.BSerializationPlugin;
import io.gridgo.bean.serialization.protobuf.ProtobufSingleSchemaSerializer;
import io.grigo.bean.protobuf.test.message.PersonOuterClass.Person;

@BSerializationPlugin(CustomProtobufSingleSchemaSerializer.NAME)
public class CustomProtobufSingleSchemaSerializer extends ProtobufSingleSchemaSerializer {

    public static final String NAME = "customProtobufSingleSchema";

    public CustomProtobufSingleSchemaSerializer() {
        super();
        this.setSchema(Person.class);
    }
}
