package io.grigo.bean.protobuf.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import io.grigo.bean.protobuf.test.message.PersonOuterClass.Person;

public class TestBeanSerializerProtobuf {

    public static void main(String[] args) throws IOException {
        Person p = Person.newBuilder().setName("Bach Hoang Nguyen").setAge(30).build();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        p.writeTo(output);
        System.out.println(Arrays.toString(output.toByteArray()));
        Person.parseFrom(output.toByteArray());
        System.out.println(Person.parseFrom(output.toByteArray()));
    }
}
