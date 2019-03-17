package io.grigo.bean.protobuf.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import io.gridgo.bean.BElement;
import io.gridgo.bean.factory.BFactory;
import io.gridgo.bean.serialization.binary.protobuf.ProtobufMultiSchemaSerializer;
import io.gridgo.bean.serialization.binary.protobuf.ProtobufSingleSchemaSerializer;
import io.grigo.bean.protobuf.test.message.PersonOuterClass.Person;

public class TestProtobufSerializer {

    @Test
    public void testMultiSchemaProtobufSerializer() throws IOException {
        ProtobufMultiSchemaSerializer protobufSerializer = BFactory.DEFAULT.getSerializerRegistry().lookup(ProtobufMultiSchemaSerializer.NAME);
        protobufSerializer.registerSchema(Person.class, 1);
        Person p = Person.newBuilder().setName("Bach Hoang Nguyen").setAge(30).build();
        BElement ele = BElement.ofAny(p);
        byte[] bytes = ele.toBytes(ProtobufMultiSchemaSerializer.NAME);
        BElement unpackedEle = BElement.ofBytes(bytes, ProtobufMultiSchemaSerializer.NAME);
        Person p2 = unpackedEle.asReference().getReference();

        assertEquals(p, p2);
    }

    @Test
    public void testSingleSchemaProtobufSerializer() throws IOException {
        ProtobufSingleSchemaSerializer protobufSerializer = BFactory.DEFAULT.getSerializerRegistry().lookup(ProtobufSingleSchemaSerializer.NAME);
        protobufSerializer.setSchema(Person.class);
        Person p = Person.newBuilder().setName("Bach Hoang Nguyen").setAge(30).build();
        BElement ele = BElement.ofAny(p);
        byte[] bytes = ele.toBytes(ProtobufSingleSchemaSerializer.NAME);
        BElement unpackedEle = BElement.ofBytes(bytes, ProtobufSingleSchemaSerializer.NAME);
        Person p2 = unpackedEle.asReference().getReference();

        assertEquals(p, p2);
    }

    @Test
    public void testCustomSingleSchemaProtobufSerializer() throws IOException {
        BFactory.DEFAULT.getSerializerRegistry().scan(CustomProtobufSingleSchemaSerializer.class.getPackageName());

        Person p = Person.newBuilder().setName("Bach Hoang Nguyen").setAge(30).build();
        BElement ele = BElement.ofAny(p);
        byte[] bytes = ele.toBytes(CustomProtobufSingleSchemaSerializer.NAME);
        BElement unpackedEle = BElement.ofBytes(bytes, CustomProtobufSingleSchemaSerializer.NAME);
        Person p2 = unpackedEle.asReference().getReference();
        assertEquals(p, p2);
    }
}
