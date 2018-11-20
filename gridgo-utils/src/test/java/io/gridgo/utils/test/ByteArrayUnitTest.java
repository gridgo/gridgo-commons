package io.gridgo.utils.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.utils.wrapper.ByteArray;

public class ByteArrayUnitTest {

	@Test
	public void testSimple() {
		var byteArray = ByteArray.newInstance(new byte[] { 1, 2, 4, 8, 16, 32, 64 });
		Assert.assertEquals(-1603446134, byteArray.hashCode());
		byteArray = ByteArray.newInstanceWithJavaSafeHashCodeCalculator(new byte[] { 1, 2, 4, 8, 16, 32, 64 });
		Assert.assertEquals(1046614007, byteArray.hashCode());
		byteArray = ByteArray.newInstanceWithJavaUnsafeHashCodeCalculator(new byte[] { 1, 2, 4, 8, 16, 32, 64 });
		Assert.assertEquals(1046614007, byteArray.hashCode());
		byteArray = ByteArray.newInstanceWithJNIHashCodeCalculator(new byte[] { 1, 2, 4, 8, 16, 32, 64 });
		Assert.assertEquals(1046614007, byteArray.hashCode());
	}
}
