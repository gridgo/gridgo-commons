package io.gridgo.framework.test;

import org.junit.Assert;
import org.junit.Test;

import io.gridgo.bean.BObject;
import io.gridgo.bean.BValue;
import io.gridgo.framework.support.Message;
import io.gridgo.framework.support.MessageConstants;
import io.gridgo.framework.support.Payload;
import io.gridgo.framework.support.impl.MultipartMessage;

public class MessageUnitTest {

	@Test
	public void testMultipart() {
		var msg1 = Message.newDefault(Payload.newDefault(BObject.newDefault().setAny("key", 1), BValue.newDefault(1)));
		var msg2 = Message.newDefault(Payload.newDefault(BObject.newDefault().setAny("key", 2), BValue.newDefault(2)));
		msg1.getPayload().addHeaderIfAbsent("key", 2);
		Assert.assertEquals(1, msg1.getPayload().getHeaders().getInteger("key"));
		msg1.getPayload().addHeaderIfAbsent("test", 2);
		Assert.assertEquals(2, msg1.getPayload().getHeaders().getInteger("test"));
		msg1.getPayload().addHeader("test", 1);
		Assert.assertEquals(1, msg1.getPayload().getHeaders().getInteger("test"));
		var msg = new MultipartMessage(new Message[] { msg1, msg2 });
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
}
