package io.gridgo.bean.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.bean.BObject;

public class BObjectUnitTest {

	@Test
	public void testSetAny() {
		BObject obj = BObject.newDefault() //
				.setAny("int", 1) //
				.setAny("str", "hello") //
				.setAny("arr", new int[] { 1, 2, 3 });
		Assert.assertEquals(1, obj.getInteger("int"));
		Assert.assertEquals("hello", obj.getString("str"));
		Assert.assertArrayEquals(new Integer[] { 1, 2, 3 }, obj.getArray("arr").toArray(new Integer[0]));
	}
}
