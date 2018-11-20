package io.gridgo.utils.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.utils.ObjectUtils;
import io.gridgo.utils.test.support.TestObject;

public class ObjectUtilsUnitTest {

	@Test
	public void testFromMap() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("testInt", 1);
		map.put("testBool", true);
		map.put("testStr", "hello");
		map.put("testArr", new int[] { 1, 2, 3 });

		var innerMap = new HashMap<>();
		innerMap.put("testInt", 2);
		map.put("testObj", innerMap);
		var obj = ObjectUtils.fromMap(TestObject.class, map);
		Assert.assertEquals(1, obj.getTestInt());
		Assert.assertEquals(true, obj.isTestBool());
		Assert.assertEquals("hello", obj.getTestStr());
		Assert.assertEquals(2, obj.getTestObj().getTestInt());
		Assert.assertArrayEquals(new int[] { 1, 2, 3 }, obj.getTestArr());

		map = ObjectUtils.toMap(obj);
		Assert.assertEquals(1, map.get("testInt"));
		Assert.assertEquals("hello", map.get("testStr"));
		Assert.assertEquals(true, map.get("testBool"));
		Assert.assertEquals(2, ((TestObject) map.get("testObj")).getTestInt());
		Assert.assertArrayEquals(new int[] { 1, 2, 3 }, (int[]) map.get("testArr"));
	}
}
