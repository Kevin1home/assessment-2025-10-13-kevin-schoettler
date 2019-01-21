package de.softconex.assessment.calcmodel;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

/**
 * Test cases for {@link Price} class.
 */
public class PriceTest extends TestCase {
	private final static Log LOG = LogFactory.getLog(PriceTest.class);

	public final void testBigDecimalConstructor1() {
		final Price price = new Price(BigDecimal.ONE);
		LOG.info("Price: " + price);
		assertEquals(price.getAmount().intValue(), 1);
	}

	public final void testBigDecimalConstructor2() {
		try {
			new Price((BigDecimal) null);
			fail();
		} catch (final Exception ex) {
			LOG.info("Caught expected " + ex.getMessage());
		}
	}

	public final void testIntegerConstructor1() {
		final Price price = new Price(Integer.valueOf(1));
		LOG.info("Price: " + price);
		assertEquals(price.getAmount().intValue(), 1);
	}

	public final void testIntegerConstructor2() {
		try {
			new Price((Integer) null);
			fail();
		} catch (final Exception ex) {
			LOG.info("Caught expected " + ex.getMessage());
		}
	}

	public final void testStringConstructor1() {
		final Price price = new Price("1");
		LOG.info("Price: " + price);
		assertEquals(price.getAmount().intValue(), 1);
	}

	public final void testStringConstructor2() {
		try {
			new Price((String) null);
			fail();
		} catch (final Exception ex) {
			LOG.info("Caught expected " + ex.getMessage());
		}
	}

	public void testAdd() {
		final Price price1 = new Price("5");

		final Price price2 = new Price("1.77");

		final Price price3 = price1.add(price2);

		assertEquals(new Price("6.77"), price3);
	}

	public void testToXmlAndParse1() {
		final Price written = new Price("7.22");

		final Element writtenElement = written.toXml();
		LOG.info("writtenElement: " + writtenElement.asXML());

		final Price parsed = Price.parse(writtenElement);
		assertEquals(written, parsed);
	}

	public void testToXmlAndParse2() {
		final Price written = new Price("-123.33");

		final Element writtenElement = written.toXml("test");
		LOG.info("writtenElement: " + writtenElement.asXML());
		assertEquals("test", writtenElement.getName());

		final Price parsed = Price.parse(writtenElement);
		assertEquals(written, parsed);
	}

	public void testParseNull() {
		assertNull(Price.parse(null));
	}

	/**
	 * Test non-static equals method
	 * 
	 */
	public void testEquals1() {
		assertEquals(new Price("5"), new Price("5"));
		assertNotSame(new Price("5"), new Price("15"));
		assertNotSame(new Price("5"), null);
		assertNotSame(new Price("5"), Boolean.FALSE);

		final Price price = new Price("10");
		assertEquals(price, price);
	}

	public void testHashCode() {
		assertEquals(new Price("5").hashCode(), new Price("5").hashCode());
	}

	/**
	 * Test static equals method.
	 * 
	 */
	public void testEquals2() {
		assertTrue(Price.equals(null, null));
		assertFalse(Price.equals(new Price("5"), null));
		assertFalse(Price.equals(null, new Price("5")));
		assertFalse(Price.equals(new Price("6"), new Price("5")));
		assertTrue(Price.equals(new Price("5"), new Price("5")));
		assertTrue(Price.equals(new Price("5"), new Price("5.0")));
		assertTrue(Price.equals(new Price("5.1"), new Price("5.10")));
	}
}
