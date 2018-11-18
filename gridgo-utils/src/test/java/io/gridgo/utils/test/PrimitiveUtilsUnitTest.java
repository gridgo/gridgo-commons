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
		Assert.assertTrue(PrimitiveUtils.getBooleanValueFrom(new byte[] {0, 0, 0, 0, 0, 0, 0, 1}));
		Assert.assertFalse(PrimitiveUtils.getBooleanValueFrom(new byte[] {0, 0, 0, 0, 0, 0, 0, 0}));
	}
}
