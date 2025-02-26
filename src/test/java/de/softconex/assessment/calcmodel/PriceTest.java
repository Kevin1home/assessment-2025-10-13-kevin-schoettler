package de.softconex.assessment.calcmodel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PriceTest extends Assertions {
    private static final Log LOG = LogFactory.getLog(PriceTest.class);

    @Test
    public final void validBigDecimalConstructor() {
        assertEquals(new Price(BigDecimal.ONE).getAmount().intValue(), 1);
    }

    @Test
    public final void invalidBigDecimalConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Price((BigDecimal) null));
    }

    @Test
    public final void validIntegerConstructor() {
        assertEquals(new Price(1).getAmount().intValue(), 1);
    }

    @Test
    public final void invalidIntegerConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Price((Integer) null));
    }

    @Test
    public final void validStringConstructor() {
        assertEquals(new Price("1").getAmount().intValue(), 1);
    }

    @Test
    public final void invalidStringConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Price((String) null));
    }

    @Test
    public void add() {
        final Price price1 = new Price("5");

        final Price price2 = new Price("1.77");

        final Price price3 = price1.add(price2);

        assertEquals(new Price("6.77"), price3);
    }

    @Test
    public void toXmlAndParseWithPositiveValue() {
        final Price written = new Price("7.22");

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final Price parsed = Price.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void toXmlAndParseWithNegativeValue() {
        final Price written = new Price("-123.33");

        final Element writtenElement = written.toXml("test");
        LOG.info("writtenElement: " + writtenElement.asXML());
        assertEquals("test", writtenElement.getName());

        final Price parsed = Price.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void parseNull() {
        assertNull(Price.parse(null));
    }

    @Test
    public void equalsMethod() {
        assertEquals(new Price("5"), new Price("5"));
        assertNotSame(new Price("5"), new Price("15"));
        assertNotSame(new Price("5"), null);
        assertNotSame(new Price("5"), Boolean.FALSE);

        final Price price = new Price("10");
        assertEquals(price, price);
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(new Price("5").hashCode(), new Price("5").hashCode());
        assertEquals(new Price("5").hashCode(), new Price("5.0").hashCode());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void staticEqualsMethod() {
        assertTrue(Price.equals(null, null));
        assertFalse(Price.equals(new Price("5"), null));
        assertFalse(Price.equals(null, new Price("5")));
        assertFalse(Price.equals(new Price("6"), new Price("5")));
        assertTrue(Price.equals(new Price("5"), new Price("5")));
        assertTrue(Price.equals(new Price("5"), new Price("5.0")));
        assertTrue(Price.equals(new Price("5.1"), new Price("5.10")));
    }
}
