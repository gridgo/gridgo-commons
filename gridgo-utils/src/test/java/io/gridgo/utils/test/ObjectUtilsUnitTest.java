package io.gridgo.utils.test;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.utils.ObjectUtils;
import io.gridgo.utils.test.support.TestObject;

public class ObjectUtilsUnitTest {

	@Test
	public void testFromMap() throws Exception {
		var map = new HashMap<String, Object>();
		map.put("testInt", 1);
		map.put("testBool", true);
		map.put("testStr", "hello");

		var innerMap = new HashMap<>();
		innerMap.put("testInt", 2);
		map.put("testObj", innerMap);
		var obj = ObjectUtils.fromMap(TestObject.class, map);
		Assert.assertEquals(1, obj.getTestInt());
		Assert.assertEquals(true, obj.isTestBool());
		Assert.assertEquals("hello", obj.getTestStr());
		Assert.assertEquals(2, obj.getTestObj().getTestInt());
	}
}
