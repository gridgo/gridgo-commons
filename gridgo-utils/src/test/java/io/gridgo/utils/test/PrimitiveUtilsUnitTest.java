package io.gridgo.utils.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.utils.PrimitiveUtils;

public class PrimitiveUtilsUnitTest {

	@Test
	public void testIsNumber() {
		Assert.assertTrue(PrimitiveUtils.isNumber((byte) 1));
		Assert.assertTrue(PrimitiveUtils.isNumber(1));
		Assert.assertTrue(PrimitiveUtils.isNumber(1.0));
		Assert.assertTrue(PrimitiveUtils.isNumber(1L));
		Assert.assertFalse(PrimitiveUtils.isNumber(null));
		Assert.assertFalse(PrimitiveUtils.isNumber("abc"));
	}

	@Test
	public void testIsPrimitive() {
		Assert.assertTrue(PrimitiveUtils.isPrimitive(Byte.class));
		Assert.assertTrue(PrimitiveUtils.isPrimitive(String.class));
		Assert.assertTrue(PrimitiveUtils.isPrimitive(Boolean.class));
		Assert.assertTrue(PrimitiveUtils.isPrimitive(Character.class));
		Assert.assertTrue(PrimitiveUtils.isPrimitive(Integer.class));
		Assert.assertTrue(PrimitiveUtils.isPrimitive(Long.class));
		Assert.assertTrue(PrimitiveUtils.isPrimitive(Double.class));
		Assert.assertFalse(PrimitiveUtils.isPrimitive(Byte[].class));
		Assert.assertFalse(PrimitiveUtils.isPrimitive(String[].class));
		Assert.assertFalse(PrimitiveUtils.isPrimitive(Object[].class));
		Assert.assertFalse(PrimitiveUtils.isPrimitive(PrimitiveUtils.class));
	}

	@Test
	public void testGetBoolean() {
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom(1));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom(0));
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom("true"));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom("false"));
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom('a'));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom('\0'));
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom(Boolean.TRUE));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom(Boolean.FALSE));
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom(new Object()));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom(null));
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom(new byte[] { 0, 0, 0, 0, 0, 0, 0, 1 }));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 }));
	}

	@Test
	public void testGetByte() {
		Assert.assertEquals(127, PrimitiveUtils.getByteValueFrom(127));
		Assert.assertEquals(-128, PrimitiveUtils.getByteValueFrom(-128));
		Assert.assertEquals(97, PrimitiveUtils.getByteValueFrom('a'));
		Assert.assertEquals(127, PrimitiveUtils.getByteValueFrom("127"));
		Assert.assertEquals(1, PrimitiveUtils.getByteValueFrom(true));
		Assert.assertEquals(0, PrimitiveUtils.getByteValueFrom(false));
		Assert.assertEquals(127, PrimitiveUtils.getByteValueFrom(new byte[] { 127 }));
	}

	@Test
	public void testGetShort() {
		Assert.assertEquals(127, PrimitiveUtils.getShortValueFrom(127));
		Assert.assertEquals(-128, PrimitiveUtils.getShortValueFrom(-128));
		Assert.assertEquals(97, PrimitiveUtils.getShortValueFrom('a'));
		Assert.assertEquals(127, PrimitiveUtils.getShortValueFrom("127"));
		Assert.assertEquals(1, PrimitiveUtils.getShortValueFrom(true));
		Assert.assertEquals(0, PrimitiveUtils.getShortValueFrom(false));
		Assert.assertEquals(257, PrimitiveUtils.getShortValueFrom(new byte[] { 1, 1 }));
	}

	@Test
	public void testGetChar() {
		Assert.assertEquals('a', PrimitiveUtils.getCharValueFrom(97));
		Assert.assertEquals('a', PrimitiveUtils.getCharValueFrom('a'));
		Assert.assertEquals('a', PrimitiveUtils.getCharValueFrom("abc"));
		Assert.assertEquals('\0', PrimitiveUtils.getCharValueFrom(""));
		Assert.assertEquals('1', PrimitiveUtils.getCharValueFrom(true));
		Assert.assertEquals('0', PrimitiveUtils.getCharValueFrom(false));
		Assert.assertEquals('a', PrimitiveUtils.getCharValueFrom(new byte[] { 0, 97 }));
	}
}
