package email.notification;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class EmailSenderTest extends TestCase {

	@Test
	public void testSender() {

	}

	@Test(expected = RuntimeException.class)
	public void testIncompleteTo() throws Exception {

		try {

			testSender();

		} catch (RuntimeException e) {
			Assert.assertEquals("Wrong exception message", "Error Sending Email");
			throw e;
		}
	}
}
