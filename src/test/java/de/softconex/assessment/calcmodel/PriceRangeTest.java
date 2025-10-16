package de.softconex.assessment.calcmodel;

import de.softconex.assessment.calcmodel.model.calculation.CalculationModelDetail;
import de.softconex.assessment.calcmodel.model.price.Price;
import de.softconex.assessment.calcmodel.model.price.PriceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceRangeTest extends Assertions {
    private static final Log LOG = LogFactory.getLog(PriceRangeTest.class);

    @Test
    public void validContains() {
        assertFalse(new PriceRange(1, 10).contains(new Price(0)));
        assertFalse(new PriceRange(1, 10).contains(new Price("0.99")));
        assertTrue(new PriceRange(1, 10).contains(new Price(1)));
        assertTrue(new PriceRange(1, 10).contains(new Price("9.99")));
        assertTrue(new PriceRange(1, 10).contains(new Price(10)));
        assertTrue(new PriceRange(1, 10).contains(new Price("1.00")));
        assertTrue(new PriceRange(1, 10).contains(new Price("10.00")));
        assertFalse(new PriceRange(1, 10).contains(new Price(11)));
        assertFalse(new PriceRange(1, 10).contains(new Price("10.01")));

        assertTrue(new PriceRange(null, new Price(10)).contains(new Price("-10.01")));
        assertTrue(new PriceRange(null, new Price(10)).contains(new Price("10.00")));
        assertTrue(new PriceRange(new Price(1), null).contains(new Price("100.01")));
        assertTrue(new PriceRange(new Price(1), null).contains(new Price("1.00")));
        assertTrue(new PriceRange(null, null).contains(new Price("10.01")));
        assertTrue(new PriceRange(null, null).contains(new Price("-10.01")));

    }

    @Test
    public void invalidContains() {
        assertThrows(IllegalArgumentException.class, () -> new PriceRange(1, 10).contains(null));
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

    @Test
    public void parseNull() {
        assertNull(CalculationModelDetail.parse(null));
    }

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
    public void hashCodeMethod() {
        assertEquals(new PriceRange(null, null).hashCode(), new PriceRange(null, null).hashCode());
        assertEquals(new PriceRange(1, 5).hashCode(), new PriceRange(1, 5).hashCode());
        assertNotSame(new PriceRange(0, 5).hashCode(), new PriceRange(1, 5).hashCode());
        assertNotSame(new PriceRange(1, 4).hashCode(), new PriceRange(1, 5).hashCode());

        final PriceRange range = new PriceRange(0, 1);
        assertEquals(range.hashCode(), range.hashCode());

        final Price price1 = new Price("5");
        final Price price2 = new Price("5.001");
        final PriceRange priceRange1 = new PriceRange(price1, null);
        final PriceRange priceRange2 = new PriceRange(price2, null);

        assertEquals(priceRange1.hashCode(), priceRange2.hashCode()); // cause of scale ignoring
    }

    @Test
    public void toStringMethod() {
        final Price price = new Price("5.001");
        final PriceRange priceRange = new PriceRange(price, null);

        assertEquals(priceRange.toString(), "[5.001 - noLimit]");

        assertNotNull(new PriceRange(null, null).toString());
        assertNotNull(new PriceRange(2, 3).toString());
    }
}
