package de.softconex.assessment.calcmodel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceRangeTest extends Assertions {
	private static final Log LOG = LogFactory.getLog(PriceRangeTest.class);

	@Test
	public void equalsMethod() {
		assertEquals(new PriceRange(null, null), new PriceRange(null, null));
		assertEquals(new PriceRange(1, 5), new PriceRange(1, 5));
		assertNotSame(new PriceRange(0, 5), new PriceRange(1, 5));
		assertNotSame(new PriceRange(1, 4), new PriceRange(1, 5));
		assertNotSame(new PriceRange(1, 4), Boolean.FALSE);

		final PriceRange range = new PriceRange(0, 1);
		assertEquals(range, range);
	}

	@Test
	public void toStringMethod() {
		assertNotNull(new PriceRange(null, null).toString());
		assertNotNull(new PriceRange(2, 3).toString());
	}

	@Test
	public void contains() {
		assertFalse(new PriceRange(1, 10).contains(new Price(0)));
		assertFalse(new PriceRange(1, 10).contains(new Price("0.99")));
		assertTrue(new PriceRange(1, 10).contains(new Price(1)));
		assertTrue(new PriceRange(1, 10).contains(new Price("1.00")));
		assertTrue(new PriceRange(1, 10).contains(new Price(10)));
		assertTrue(new PriceRange(1, 10).contains(new Price("10.00")));
		assertFalse(new PriceRange(1, 10).contains(new Price(11)));
		assertFalse(new PriceRange(1, 10).contains(new Price("10.01")));
	}

	@Test
	public void toXmlAndParseIntervalOpenBothSides() {
		final PriceRange written = new PriceRange(null, null);

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}

	@Test
	public void toXmlAndParseIntervalRightOpen() {
		final PriceRange written = new PriceRange(new Price(1), null);

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}

	@Test
	public void toXmlAndParseIntervalLeftOpen() {
		final PriceRange written = new PriceRange(null, new Price(2));

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}

	@Test
	public void toXmlAndParseIntervalClosed() {
		final PriceRange written = new PriceRange(new Price(1), new Price(2));

		final Element writtenElement = written.toXml();
		LOG.info("written: " + writtenElement.asXML());

		final PriceRange parsed = PriceRange.parse(writtenElement);
		assertNotNull(parsed);
		assertEquals(parsed, written);
	}
}
