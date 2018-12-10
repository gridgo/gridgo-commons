package io.gridgo.bean.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.bean.BElement;
import io.gridgo.bean.BValue;

public class BValueUnitTest {

    @Test
    public void testEncodeDecode() {
        var val = BValue.of(new byte[] { 1, 2, 4, 8, 16, 32, 64 });
        val.encodeHex();
        Assert.assertEquals("0x01020408102040", val.getData());
        val.decodeHex();
        Assert.assertArrayEquals(new byte[] { 1, 2, 4, 8, 16, 32, 64 }, (byte[]) val.getData());
        val.encodeBase64();
        Assert.assertEquals("AQIECBAgQA==", val.getData());
        val.decodeBase64();
        Assert.assertArrayEquals(new byte[] { 1, 2, 4, 8, 16, 32, 64 }, (byte[]) val.getData());

        val = BElement.fromJson(val.toJson()).asValue();
        val.decodeHex();
        Assert.assertArrayEquals(new byte[] { 1, 2, 4, 8, 16, 32, 64 }, (byte[]) val.getData());

        val = BElement.fromXml(val.toXml()).asValue();
        Assert.assertArrayEquals(new byte[] { 1, 2, 4, 8, 16, 32, 64 }, (byte[]) val.getData());
    }
}
