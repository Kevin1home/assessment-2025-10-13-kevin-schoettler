package de.softconex.assessment.calcmodel;

import de.softconex.assessment.calcmodel.model.price.Price;
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
        assertThrows(IllegalArgumentException.class, () -> new Price(" "));
    }

    @Test
    public void validAdd() {
        final Price price0 = new Price("5");
        final Price price1 = new Price("1.77");
        final Price price2 = price0.add(price1);

        assertEquals(new Price("6.77"), price2);
    }

    @Test
    public void invalidAdd() {
        final Price price = new Price("5");

        assertThrows(IllegalArgumentException.class, () -> price.add(null));
    }

    @Test
    public void validMultiplyBigDecimal() {
        final Price price0 = new Price("5.77");
        final Price price1 = price0.multiplyBigDecimal(BigDecimal.ONE);

        assertEquals(new Price("5.77"), price1);
    }

    @Test
    public void invalidMultiplyBigDecimal() {
        final Price price = new Price("5");

        assertThrows(IllegalArgumentException.class, () -> price.multiplyBigDecimal(null));
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
    public void invalidToXml() {
        final Price written = new Price("-123.33");

        assertThrows(IllegalArgumentException.class, () -> written.toXml(null));
        assertThrows(IllegalArgumentException.class, () -> written.toXml(" "));
    }

    @Test
    public void parseNull() {
        assertNull(Price.parse(null));
    }

    @Test
    public void equalsMethod() {
        assertEquals(new Price("5"), new Price("5"));
        assertEquals(new Price("5"), new Price("5.0"));
        assertNotSame(new Price("5"), new Price("15"));
        assertNotSame(new Price("5"), new Price("5.0001"));
        assertNotSame(new Price("5"), null);
        assertNotSame(new Price("5"), Boolean.FALSE);

        final Price price = new Price("10");
        assertEquals(price, price);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void staticEqualsMethod() {
        assertTrue(Price.equals(null, null));
        assertFalse(Price.equals(new Price("5"), null));
        assertFalse(Price.equals(null, new Price("5")));
        assertFalse(Price.equals(new Price("6"), new Price("5")));
        assertFalse(Price.equals(new Price("5"), new Price("5.001")));
        assertTrue(Price.equals(new Price("5"), new Price("5")));
        assertTrue(Price.equals(new Price("5"), new Price("5.0")));
        assertTrue(Price.equals(new Price("5.1"), new Price("5.10")));
    }

    @Test
    public void hashCodeMethod() {
        assertNotSame(new Price("5").hashCode(), new Price("6").hashCode());
        assertEquals(new Price("5").hashCode(), new Price("5").hashCode());
        assertEquals(new Price("5").hashCode(), new Price("5.0").hashCode());
        assertEquals(new Price("5").hashCode(), new Price("5.001").hashCode()); // cause of scale ignoring

    }

    @Test
    public void toStringMethod() {
        assertEquals(new Price("5").toString(), "5");
    }
}
