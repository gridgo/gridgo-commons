package io.gridgo.bean.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;

public class BObjectUnitTest {

	@Test
	public void testSetAny() {
		BObject obj = BObject.newDefault() //
				.set("int", BValue.newDefault(1)) //
				.setAny("str", "hello") //
				.setAny("arr", new int[] { 1, 2, 3 });
		Assert.assertEquals(1, obj.getInteger("int"));
		Assert.assertEquals("hello", obj.getString("str"));
		Assert.assertArrayEquals(new Integer[] { 1, 2, 3 }, //
				obj.getArray("arr").stream() //
						.map(e -> e.asValue().getData()) //
						.toArray(size -> new Integer[size]));
	}
}
