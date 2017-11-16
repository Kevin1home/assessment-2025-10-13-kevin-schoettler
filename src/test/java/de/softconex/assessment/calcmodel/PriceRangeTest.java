package de.softconex.assessment.calcmodel;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

/**
 * Test cases for {@link PriceRange} class.
 */
public class PriceRangeTest extends TestCase {
	private final static Log LOG = LogFactory.getLog(PriceRangeTest.class);

	public void testEquals1() {
		assertEquals(new PriceRange(null, null), new PriceRange(null, null));
		assertEquals(new PriceRange(1, 5), new PriceRange(1, 5));
		assertNotSame(new PriceRange(0, 5), new PriceRange(1, 5));
		assertNotSame(new PriceRange(1, 4), new PriceRange(1, 5));
		assertNotSame(new PriceRange(1, 4), Boolean.FALSE);

		final PriceRange range = new PriceRange(0, 1);
		assertTrue(range.equals(range));
	}

	public void testToString() {
		// make sure no NullPointerException is thrown for ranges w/o
		// minimum/maximum
		assertNotNull(new PriceRange(null, null).toString());
		assertNotNull(new PriceRange(2, 3).toString());
	}

	public void testContains() {
		assertFalse(new PriceRange(1, 10).contains(new Price(0)));
		assertFalse(new PriceRange(1, 10).contains(new Price("0.99")));
		assertTrue(new PriceRange(1, 10).contains(new Price(1)));
		assertTrue(new PriceRange(1, 10).contains(new Price("1.00")));
		assertTrue(new PriceRange(1, 10).contains(new Price(10)));
		assertTrue(new PriceRange(1, 10).contains(new Price("10.00")));
		assertFalse(new PriceRange(1, 10).contains(new Price(11)));
		assertFalse(new PriceRange(1, 10).contains(new Price("10.01")));
	}

	public void testToXmlAndParse1() {
		final PriceRange written = new PriceRange(null, null);

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}

	public void testToXmlAndParse2() {
		final PriceRange written = new PriceRange(new Price(1), null);

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}

	public void testToXmlAndParse3() {
		final PriceRange written = new PriceRange(null, new Price(2));

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}

	public void testToXmlAndParse4() {
		final PriceRange written = new PriceRange(new Price(1), new Price(2));

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}
}
