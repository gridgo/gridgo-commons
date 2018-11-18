package io.gridgo.utils.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.utils.ByteArrayUtils;

public class TestByteArrayUtils {

	@Test
	public void testHex() {
		byte[] bytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		String origin = Arrays.toString(bytes);

		String hex = ByteArrayUtils.toHex(bytes, "0x");

		byte[] dehexBytes = ByteArrayUtils.fromHex(hex);
		String dehex = Arrays.toString(dehexBytes);

		Assert.assertEquals(origin, dehex);
	}

	@Test
	public void testConcat() {
		byte[] b1 = new byte[] { 1, 2 };
		byte[] b2 = new byte[] { 3, 4 };
		byte[] result = ByteArrayUtils.concat(b1, b2);
		Assert.assertEquals(4, result.length);
		Assert.assertEquals(1, result[0]);
		Assert.assertEquals(2, result[1]);
		Assert.assertEquals(3, result[2]);
		Assert.assertEquals(4, result[3]);
		result = ByteArrayUtils.concat();
		Assert.assertEquals(0, result.length);
	}
	
	@Test
	public void testPrimitives() {
		boolean bool = ByteArrayUtils.primitiveFromByteArray(Boolean.class, new byte[] {0, 0, 0, 0, 0, 0, 0, 1});
		Assert.assertTrue(bool);
		byte b = ByteArrayUtils.primitiveFromByteArray(Byte.class, new byte[] {1});
		Assert.assertEquals(1, b);
		short sh = ByteArrayUtils.primitiveFromByteArray(Short.class, new byte[] {1, 1});
		Assert.assertEquals(257, sh);
		int i = ByteArrayUtils.primitiveFromByteArray(Integer.class, new byte[] {1, 1, 1, 1});
		Assert.assertEquals(16843009, i);
		long l = ByteArrayUtils.primitiveFromByteArray(Long.class, new byte[] {1, 0, 0, 0, 0, 0, 0, 0});
		Assert.assertEquals(72057594037927936L, l);
		char c = ByteArrayUtils.primitiveFromByteArray(Character.class, new byte[] {0, 97});
		Assert.assertEquals('a', c);
	}
}
