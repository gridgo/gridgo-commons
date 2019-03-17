package io.gridgo.bean.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;

public class TextJsonSerializer {

    @Test
    public void testJsonSerializer() {
        var obj = BObject.ofEmpty() //
                         .setAny("bool", false) //
                         .set("int", BValue.of(1)) //
                         .setAny("long", 1L) //
                         .setAny("char", 'a') //
                         .setAny("str", "hello") //
                         .setAny("double", 1.11) //
                         .setAny("byte", (byte) 1) //
                         .setAny("raw", new byte[] { 1, 2, 3, 4, 5, 6 }) //
                         .setAny("arr", new int[] { 1, 2, 3 }) //
                         .set("obj", BObject.ofEmpty().setAny("int", 2)) //
        ;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        obj.writeBytes(out, "json");
        byte[] bytes = out.toByteArray();

        BElement unpackedEle = BElement.ofBytes(new ByteArrayInputStream(bytes), "json");
        assertNotNull(unpackedEle);
        assertTrue(unpackedEle.isObject());
        unpackedEle.asObject().getValue("raw").decodeHex();

        assertEquals(obj, unpackedEle);
    }
}
