package de.softconex.assessment.calcmodel;

import de.softconex.assessment.calcmodel.model.calculation.CalculationModelDetail;
import de.softconex.assessment.calcmodel.model.calculation.CalculationModelDetailList;
import de.softconex.assessment.calcmodel.model.price.Price;
import de.softconex.assessment.calcmodel.model.price.PriceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CalculationModelDetailListTest extends Assertions {
    private static final Log LOG = LogFactory.getLog(CalculationModelDetailListTest.class);

    @Test
    public final void found() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list.add(detail0);
        detail0.setPriceRange(new PriceRange(0, 19));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list.add(detail1);
        detail1.setPriceRange(new PriceRange(20, 29));

        assertEquals(detail0, list.find(new Price(19)));
        assertEquals(detail1, list.find(new Price(20)));
        assertNull(list.find(new Price(30)));
    }

    @Test
    public final void notFound() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list.add(detail0);
        detail0.setPriceRange(new PriceRange(0, 19));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list.add(detail1);

        assertEquals(detail0, list.find(new Price(19)));
        assertEquals(detail1, list.find(new Price(20)));
        assertNotNull(list.find(new Price(30)));
    }

    @Test
    public final void invalidFind() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        assertThrows(IllegalArgumentException.class, () -> list.find(null));
    }

    @Test
    public void sort() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list.add(detail0);
        detail0.setPriceRange(new PriceRange(20, 29));

        list.add(null);

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list.add(detail1);
        detail1.setPriceRange(new PriceRange(0, 19));

        list.sortByMinimumAscending();
        assertEquals(detail1, list.get(0));
        assertEquals(detail0, list.get(1));
        assertNull(list.get(2));
    }

    @Test
    public void toXmlAndParseFull() {
        final CalculationModelDetailList written = new CalculationModelDetailList();

        final CalculationModelDetail detail0 = new CalculationModelDetail();
        written.add(detail0);
        detail0.setPerCent(new BigDecimal("0.10"));
        detail0.setAbsolute(new Price(10));
        detail0.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        written.add(detail1);

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModelDetailList parsed = CalculationModelDetailList.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void toXmlAndParseEmpty() {
        final CalculationModelDetailList written = new CalculationModelDetailList();

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModelDetailList parsed = CalculationModelDetailList.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void parseNull() {
        assertNull(CalculationModelDetailList.parse(null));
    }

    @Test
    public void toStringMethod() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        final CalculationModelDetail detail = new CalculationModelDetail();
        list.add(detail);
        detail.setPerCent(new BigDecimal("0.10"));
        detail.setAbsolute(new Price(10));

        assertEquals(list.toString(), "[CalculationModelDetail{perCent=0.10, absolute=10, priceRange=null}]");
        assertNotNull(new CalculationModelDetailList().toString());
    }
}
