package id.gridgo.bean.serialization.binary.avro.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import id.gridgo.bean.serialization.binary.avro.test.support.Person;
import io.gridgo.bean.BElement;
import io.gridgo.bean.factory.BFactory;
import io.gridgo.bean.serialization.binary.avro.AvroSerializer;
import io.gridgo.utils.ByteArrayUtils;

public class TestAvroSerializer {

    @Test
    public void testAvroSerializer() throws IOException {
        AvroSerializer avroSerializer = BFactory.DEFAULT.getSerializerRegistry().lookup(AvroSerializer.NAME);
        avroSerializer.registerSchema(Person.class, 1);

        Person p = Person.newBuilder().setName("Bach Hoang Nguyen").setAge(30).build();
        byte[] bytes = BElement.ofAny(p).toBytes(AvroSerializer.NAME);
        System.out.println(ByteArrayUtils.toHex(bytes, "0x"));

        BElement unpackedEle = BElement.ofBytes(bytes, AvroSerializer.NAME);
        Person p2 = unpackedEle.asReference().getReference();

        assertEquals(p, p2);

        System.out.println(p2);
    }
}
