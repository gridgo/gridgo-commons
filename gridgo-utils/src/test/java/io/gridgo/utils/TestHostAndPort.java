package io.gridgo.utils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import io.gridgo.utils.support.HostAndFixedPort;
import io.gridgo.utils.support.HostAndPort;
import io.gridgo.utils.support.HostAndPortSet;

public class TestHostAndPort {

	@Test
	public void testHostAndPort() {
		HostAndPort hostAndPort = HostAndPort.newInstance("localhost", 8080);
		assertNotNull(hostAndPort);
		assertEquals(hostAndPort.getHost(), "localhost");
		assertEquals(hostAndPort.getPort(), 8080);
		assertEquals(hostAndPort.getResolvedIp(), "127.0.0.1");
		assertEquals(hostAndPort.toString(), "localhost:8080");
		assertEquals(hostAndPort.toHostAndPort(), "localhost:8080");
		assertEquals(hostAndPort.toIpAndPort(), "127.0.0.1:8080");

		hostAndPort = HostAndPort.fromString("localhost:8080");
		assertNotNull(hostAndPort);
		assertEquals(hostAndPort.getHost(), "localhost");
		assertEquals(hostAndPort.getPort(), 8080);
		assertEquals(hostAndPort.getResolvedIp(), "127.0.0.1");
		assertEquals(hostAndPort.toString(), "localhost:8080");
		assertEquals(hostAndPort.toHostAndPort(), "localhost:8080");
		assertEquals(hostAndPort.toIpAndPort(), "127.0.0.1:8080");

		hostAndPort = new HostAndFixedPort("localhost", 8080);
		assertEquals(hostAndPort.getHost(), "localhost");
		assertEquals(hostAndPort.getPort(), 8080);
		assertEquals(hostAndPort.getResolvedIp(), "127.0.0.1");
		assertEquals(hostAndPort.toString(), "localhost:8080");
		assertEquals(hostAndPort.toHostAndPort(), "localhost:8080");
		assertEquals(hostAndPort.toIpAndPort(), "127.0.0.1:8080");

		Exception ex = null;
		try {
			hostAndPort.setPort(9090);
		} catch (Exception e) {
			ex = e;
		}

		assertTrue(ex instanceof UnsupportedOperationException);

		HostAndPort otherHostAndPort = HostAndPort.fromString("127.0.0.1:8080");
		assertEquals(hostAndPort, otherHostAndPort);

		otherHostAndPort = HostAndPort.fromString("127.0.0.1:9999");
		assertNotEquals(hostAndPort, otherHostAndPort);

		otherHostAndPort = HostAndPort.fromString("this-is-invalid-host:9999");
		assertNotEquals(hostAndPort, otherHostAndPort);

		List<HostAndPort> list = HostAndPort.parse("localhost:8080, 127.0.0.1:8080,localhost,* :9999,0.0.0.0:9999");
		assertNotNull(list);
		assertEquals(list.size(), 5);
		assertEquals(list.get(0), list.get(1));
		assertEquals(list.get(3), list.get(4));
		assertNotEquals(list.get(0), list.get(2));
		
		list = HostAndPort.parse("[localhost:8080, 127.0.0.1:8080,localhost,* :9999,0.0.0.0:9999]");
		assertNotNull(list);
		assertEquals(list.size(), 5);
		assertEquals(list.get(0), list.get(1));
		assertEquals(list.get(3), list.get(4));
		assertNotEquals(list.get(0), list.get(2));
		System.out.println("list: " + list);
	}

	@Test
	public void testHostAndPortSet() {
		HostAndPortSet set = new HostAndPortSet("localhost:8080, 127.0.0.1:8080,localhost,* :9999,0.0.0.0:9999");
		assertNotNull(set);
		assertEquals(3, set.size());

		set = new HostAndPortSet("localhost:8080");
		assertNotNull(set);
		assertEquals(1, set.size());

		HostAndPort hostAndPort = HostAndPort.newInstance("localhost", 8080);
		assertEquals(hostAndPort, set);
	}
}
