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
}
