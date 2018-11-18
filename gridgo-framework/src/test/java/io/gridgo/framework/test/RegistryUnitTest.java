package io.gridgo.framework.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.framework.support.exceptions.BeanNotFoundException;
import io.gridgo.framework.support.impl.SimpleRegistry;

public class RegistryUnitTest {

	@Test
	public void testRegistry() throws InterruptedException {
		var reg = new SimpleRegistry().register("name", "dungba").register("age", 10);
		Assert.assertEquals("dungba", reg.lookup("name"));
		Assert.assertEquals(10, reg.lookup("age"));
		Assert.assertNull(reg.lookup("dob"));
		try {
			reg.lookupMandatory("dob");
			Assert.fail("must fail");
		} catch (BeanNotFoundException ex) {

		}
		try {
			reg.lookupMandatory("age", String.class);
			Assert.fail("must fail");
		} catch (ClassCastException ex) {

		}
	}
}
