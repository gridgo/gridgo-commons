package io.gridgo.framework.test;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.bean.BArray;
import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.Message;
import io.gridgo.framework.support.MessageConstants;
import io.gridgo.framework.support.Payload;
import io.gridgo.framework.support.impl.MultipartMessage;

public class MessageUnitTest {

	@Test
	public void testMessage() {
		var msg1 = Message.newDefault(Payload.newDefault(BObject.newDefault().setAny("key", 1), BValue.newDefault(1)));
		msg1.getPayload().addHeaderIfAbsent("key", 2);
		Assert.assertEquals(1, msg1.getPayload().getHeaders().getInteger("key"));
		msg1.getPayload().addHeaderIfAbsent("test", 2);
		Assert.assertEquals(2, msg1.getPayload().getHeaders().getInteger("test"));
		msg1.getPayload().addHeader("test", 1);
		Assert.assertEquals(1, msg1.getPayload().getHeaders().getInteger("test"));
		msg1.attachSource("test");
		Assert.assertEquals("test", msg1.getMisc().get(MessageConstants.SOURCE));
		msg1.setRoutingIdFromAny(1);
		Assert.assertEquals(1, msg1.getRoutingId().get().getInteger());
		var msg2 = Message.newDefault(BValue.newDefault(1), Payload.newDefault(null));
		Assert.assertEquals(1, msg2.getRoutingId().get().getInteger());
		var msg3 = Message.newDefault(BValue.newDefault(1), Collections.singletonMap("test", 1),
				Payload.newDefault(null));
		Assert.assertEquals(1, msg3.getMisc().get("test"));
		var msg4 = Message
				.parse(BArray.newDefault().addAny(1).addAny(BObject.newDefault().setAny("test", 1)).addAny(1));
		Assert.assertEquals(1, msg4.getPayload().getId().get().getInteger());
		Assert.assertEquals(1, msg4.getPayload().getHeaders().getInteger("test"));
		Assert.assertEquals(1, msg4.getPayload().getBody().asValue().getInteger());
	}

	@Test
	public void testMultipart() {
		var msg1 = Message.newDefault(Payload.newDefault(BObject.newDefault().setAny("key", 1), BValue.newDefault(1)));
		var msg2 = Message.newDefault(Payload.newDefault(BObject.newDefault().setAny("key", 2), BValue.newDefault(2)));
		var multipart1 = new MultipartMessage(new Message[] { msg1, msg2 });
		assertMessage(multipart1);
		var multipart2 = new MultipartMessage((Iterable<Message>) Arrays.asList(new Message[] { msg1, msg2 }));
		assertMessage(multipart2);
	}

	private void assertMessage(MultipartMessage msg) {
		Assert.assertEquals(true, msg.getPayload().getHeaders().getBoolean(MessageConstants.IS_MULTIPART));
		Assert.assertEquals(2, msg.getPayload().getHeaders().getInteger(MessageConstants.SIZE));
		Assert.assertTrue(msg.getPayload().getBody().isArray());
		Assert.assertEquals(2, msg.getPayload().getBody().asArray().size());
		Assert.assertEquals(1, msg.getPayload().getBody().asArray().getObject(0).getObject(MessageConstants.HEADERS)
				.getInteger("key"));
		Assert.assertEquals(2, msg.getPayload().getBody().asArray().getObject(1).getObject(MessageConstants.HEADERS)
				.getInteger("key"));
		Assert.assertEquals(1, msg.getPayload().getBody().asArray().getObject(0).getInteger(MessageConstants.BODY));
		Assert.assertEquals(2, msg.getPayload().getBody().asArray().getObject(1).getInteger(MessageConstants.BODY));
	}

	@Test
	public void testPayload() {
		var payload = Payload.newDefault(BValue.newDefault(1), BValue.newDefault(2));
		Assert.assertEquals(1, payload.getId().get().getInteger());
		Assert.assertEquals(2, payload.getBody().asValue().getInteger());
		payload.setId("test");
		Assert.assertEquals("test", payload.getId().get().getString());
		payload.setBody(BObject.newDefault().setAny("test", 1));
		Assert.assertEquals(1, payload.getBody().asObject().getInteger("test"));
	}
}
