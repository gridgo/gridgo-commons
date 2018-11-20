package io.gridgo.utils.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.utils.ThreadUtils;

public class ThreadUtilsUnitTest {

	@Test
	public void testSimple() {
		int id = ThreadUtils.registerShutdownTask(() -> System.out.println("Shutting down..."));
		Assert.assertNotEquals(-1, id);
		boolean bool = ThreadUtils.deregisterShutdownTask(id);
		Assert.assertTrue(bool);
		ThreadUtils.registerShutdownTask(() -> System.out.println("Shutting down..."));
	}
}
