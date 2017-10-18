package de.softconex.assessment;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple test cases illustrating how to test both success and exceptions.
 */
public class SimpleTest extends TestCase {
	private final static Log LOG = LogFactory.getLog(SimpleTest.class);

	public void testForSuccess() {
		final String testString = "Hello World";

		assertEquals("Hello", testString.substring(0, 5));
	}

	public void testForException() {
		final String testString = "Hello World";

		try {
			assertEquals("Hello", testString.substring(100));
			fail();
		} catch (final Exception ex) {
			LOG.info("Caught expected exception " + ex.getMessage());
		}
	}
}
