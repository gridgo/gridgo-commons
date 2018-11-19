package io.gridgo.bean.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;

public class BObjectUnitTest {

	@Test
	public void testSetAny() {
		BObject obj = BObject.newDefault() //
				.set("int", BValue.newDefault(1)) //
				.setAny("long", 1L) //
				.setAny("char", 'a') //
				.setAny("str", "hello") //
				.setAny("double", 1.11) //
				.setAny("arr", new int[] { 1, 2, 3 });
		assertObject(obj);
		assertObject(obj.deepClone());
		obj.setAnyIfAbsent("arr", 1);
		assertObject(obj);
		Assert.assertTrue(obj.getBoolean("bool", true));
		obj.setAny("bool", true);
		Assert.assertTrue(obj.getBoolean("bool", false));
		Assert.assertEquals(1L, obj.getLong("long", -1));
		Assert.assertEquals('a', obj.getChar("char", '\0'));
		Assert.assertEquals(1.11, obj.getDouble("double", -1), 0);
		Assert.assertEquals(1.11, obj.getFloat("double", -1), 0.001);
	}

	private void assertObject(BObject obj) {
		Assert.assertEquals(1, obj.getInteger("int", -1));
		Assert.assertEquals("hello", obj.getString("str", null));
		Assert.assertArrayEquals(new Integer[] { 1, 2, 3 }, //
				obj.getArray("arr", BArray.newDefault()).stream() //
						.map(e -> e.asValue().getData()) //
						.toArray(size -> new Integer[size]));
	}
}
