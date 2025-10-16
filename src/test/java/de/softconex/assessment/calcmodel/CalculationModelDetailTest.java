package de.softconex.assessment.calcmodel;

import de.softconex.assessment.calcmodel.model.calculation.CalculationModelDetail;
import de.softconex.assessment.calcmodel.model.price.Price;
import de.softconex.assessment.calcmodel.model.price.PriceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CalculationModelDetailTest extends Assertions {

    private static final Log LOG = LogFactory.getLog(CalculationModelDetailTest.class);

    @Test
    public void invalidCalculate() {
        final CalculationModelDetail detail = new CalculationModelDetail();
        detail.setPriceRange(new PriceRange(2, 10));
        final Price price = new Price(1);

        assertThrows(IllegalArgumentException.class, () -> detail.calculate(null, true));
        assertThrows(IllegalArgumentException.class, () -> detail.calculate(price, true));
    }

    @Test
    public void calculateWithPerCentAndAbsolute() {
        final CalculationModelDetail detail = new CalculationModelDetail();
        detail.setPerCent(new BigDecimal("0.10"));
        detail.setAbsolute(new Price(10));

        final Price in = new Price(100);

        final Price calculated0 = detail.calculate(in, true);
        final Price calculated1 = detail.calculate(in, false);

        assertEquals(new Price(120), calculated0);
        assertEquals(new Price(121), calculated1);
    }

    @Test
    public void calculatePerCentOnly() {
        final CalculationModelDetail detail = new CalculationModelDetail();
        detail.setPerCent(new BigDecimal("0.10"));

        final Price in = new Price(10);

        final Price calculated0 = detail.calculate(in, true);
        final Price calculated1 = detail.calculate(in, false);

        assertEquals(new Price(11), calculated0);
        assertEquals(new Price(11), calculated1);
    }

    @Test
    public void calculateAbsoluteOnly() {
        final CalculationModelDetail detail = new CalculationModelDetail();
        detail.setAbsolute(new Price(5));

        final Price in = new Price(1);

        final Price calculated0 = detail.calculate(in, true);
        final Price calculated1 = detail.calculate(in, false);

        assertEquals(new Price(6), calculated0);
        assertEquals(new Price(6), calculated1);
    }

    @Test
    public void calculateAbsoluteNullAndPerCentNull() {
        final CalculationModelDetail detail = new CalculationModelDetail();
        final Price in = new Price(1);

        final Price calculated0 = detail.calculate(in, true);
        final Price calculated1 = detail.calculate(in, false);

        assertEquals(new Price(1), calculated0);
        assertEquals(new Price(1), calculated1);
    }

    @Test
    public void toXmlAndParseWithAll() {
        final CalculationModelDetail written = new CalculationModelDetail();
        written.setPerCent(new BigDecimal("0.10"));
        written.setAbsolute(new Price(10));
        written.setPriceRange(new PriceRange(1, 199));

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModelDetail parsed = CalculationModelDetail.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void toXmlAndParseWithoutAll() {
        final CalculationModelDetail written = new CalculationModelDetail();
        written.setPerCent(new BigDecimal("0.10"));
        written.setAbsolute(new Price(10));

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModelDetail parsed = CalculationModelDetail.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void toXmlAndParseWithoutPriceRange() {
        final CalculationModelDetail written = new CalculationModelDetail();

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModelDetail parsed = CalculationModelDetail.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void parseNull() {
        assertNull(CalculationModelDetail.parse(null));
    }

    @Test
    public void equalsMethod() {
        final CalculationModelDetail detail0 = new CalculationModelDetail();
        detail0.setPerCent(new BigDecimal("0.10"));
        detail0.setAbsolute(new Price(10));
        detail0.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        detail1.setPerCent(new BigDecimal("0.10"));
        detail1.setAbsolute(new Price(10));
        detail1.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail2 = new CalculationModelDetail();
        detail2.setPerCent(new BigDecimal("0.10"));
        detail2.setAbsolute(new Price(10));

        assertEquals(new CalculationModelDetail(), new CalculationModelDetail());
        assertEquals(detail0, detail1);
        assertNotSame(new CalculationModelDetail(), detail0);
        assertNotSame(detail0, detail2);
        assertNotSame(detail0, Boolean.FALSE);
        assertEquals(detail0, detail0);
    }

    @Test
    public void hashCodeMethod() {
        final CalculationModelDetail detail0 = new CalculationModelDetail();
        detail0.setPerCent(new BigDecimal("0.10"));
        detail0.setAbsolute(new Price(10));
        detail0.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        detail1.setPerCent(new BigDecimal("0.101"));
        detail1.setAbsolute(new Price(10));
        detail1.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail2 = new CalculationModelDetail();
        detail2.setPerCent(new BigDecimal("0.10"));
        detail2.setAbsolute(new Price(10));

        assertEquals(new CalculationModelDetail().hashCode(), new CalculationModelDetail().hashCode());
        assertEquals(detail0.hashCode(), detail1.hashCode()); // cause of scale ignoring
        assertNotSame(detail0.hashCode(), detail2.hashCode());
        assertEquals(detail0.hashCode(), detail0.hashCode());
    }

    @Test
    public void toStringMethod() {

        final CalculationModelDetail detail = new CalculationModelDetail();
        detail.setPerCent(new BigDecimal("0.10"));
        detail.setAbsolute(new Price(10));

        assertEquals(detail.toString(), "CalculationModelDetail{perCent=0.10, absolute=10, priceRange=null}");
        assertNotNull(new CalculationModelDetail().toString());
    }
}
