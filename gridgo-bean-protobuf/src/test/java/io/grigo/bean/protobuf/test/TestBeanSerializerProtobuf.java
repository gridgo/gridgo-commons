package io.grigo.bean.protobuf.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import io.gridgo.bean.BElement;
import io.gridgo.bean.factory.BFactory;
import io.gridgo.bean.serialization.binary.protobuf.ProtobufSerializer;
import io.grigo.bean.protobuf.test.message.PersonOuterClass.Person;

public class TestBeanSerializerProtobuf {

    @Test
    public void testProtobufSerializer() throws IOException {
        ProtobufSerializer protobufSerializer = BFactory.DEFAULT.getSerializerRegistry().lookup(ProtobufSerializer.NAME);
        protobufSerializer.registerSchema(Person.class, 1);
        Person p = Person.newBuilder().setName("Bach Hoang Nguyen").setAge(30).build();
        BElement ele = BElement.ofAny(p);
        byte[] bytes = ele.toBytes(ProtobufSerializer.NAME);
        BElement unpackedEle = BElement.ofBytes(bytes, ProtobufSerializer.NAME);
        Person p2 = unpackedEle.asReference().getReference();

        assertEquals(p, p2);
    }
}
