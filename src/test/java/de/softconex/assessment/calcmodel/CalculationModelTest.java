package de.softconex.assessment.calcmodel;

import de.softconex.assessment.calcmodel.model.calculation.CalculationModel;
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

public class CalculationModelTest extends Assertions {
    private static final Log LOG = LogFactory.getLog(CalculationModelTest.class);

    @Test
    public final void validConstructorWithCalculationModelDetailList() {
        CalculationModelDetailList list = new CalculationModelDetailList();
        CalculationModelDetail detail = new CalculationModelDetail();
        list.add(detail);
        CalculationModel model = new CalculationModel(list);

        assertFalse(model.getCalculationModelDetailList().isEmpty());
    }

    @Test
    public final void invalidConstructorWithCalculationModelDetailList() {
        assertThrows(IllegalArgumentException.class, () -> new CalculationModel(null));
    }

    @Test
    public void invalidCalculate() {
        final CalculationModel model = new CalculationModel();
        assertThrows(IllegalArgumentException.class, () -> model.calculate(null));
        assertThrows(IllegalArgumentException.class, () -> model.calculate(null, true));
        assertThrows(IllegalArgumentException.class, () -> model.calculate(null, false));
    }

    @Test
    public void calculateWithNotMatchingCalculationModelDetail() {
        final CalculationModel model = new CalculationModel();

        assertNull(model.calculate(new Price(10)));
        assertNull(model.calculate(new Price(10), true));
        assertNull(model.calculate(new Price(10), false));
    }

    @Test
    public void calculateWithMatchingCalculationModelDetail() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list.add(detail0);
        detail0.setPerCent(new BigDecimal("0.10"));
        detail0.setAbsolute(new Price(10));
        detail0.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list.add(detail1);

        final CalculationModel model = new CalculationModel(list);

        assertEquals(model.calculate(new Price(100)), new Price(120));
        assertEquals(model.calculate(new Price(100), true), new Price(120));
        assertEquals(model.calculate(new Price(100), false), new Price(121));
    }

    @Test
    public void toXmlAndParseFull() {
        final CalculationModelDetailList list = new CalculationModelDetailList();

        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list.add(detail0);
        detail0.setPerCent(new BigDecimal("0.10"));
        detail0.setAbsolute(new Price(10));
        detail0.setPriceRange(new PriceRange(1, 199));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list.add(detail1);

        final CalculationModel written = new CalculationModel(list);

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModel parsed = CalculationModel.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void toXmlAndParseEmpty() {
        final CalculationModel written = new CalculationModel();

        final Element writtenElement = written.toXml();
        LOG.info("writtenElement: " + writtenElement.asXML());

        final CalculationModel parsed = CalculationModel.parse(writtenElement);
        assertEquals(written, parsed);
    }

    @Test
    public void parseNull() {
        assertNull(CalculationModel.parse(null));
    }

    @Test
    public void equalsMethod() {

        final CalculationModelDetailList list0 = new CalculationModelDetailList();
        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list0.add(detail0);
        detail0.setPerCent(new BigDecimal("0.10"));

        final CalculationModelDetailList list1 = new CalculationModelDetailList();
        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list1.add(detail1);

        final CalculationModel model0 = new CalculationModel(list0);
        final CalculationModel model1 = new CalculationModel(list0);
        final CalculationModel model2 = new CalculationModel(list1);

        assertEquals(new CalculationModel(), new CalculationModel());
        assertEquals(model0, model1);
        assertNotSame(model0, new CalculationModel());
        assertNotSame(model0, model2);
        assertNotSame(model0, Boolean.FALSE);
        assertEquals(model0, model0);
    }

    @Test
    public void hashCodeMethod() {
        final CalculationModelDetailList list0 = new CalculationModelDetailList();
        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list0.add(detail0);
        detail0.setPerCent(new BigDecimal("0.10"));

        final CalculationModelDetailList list1 = new CalculationModelDetailList();
        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list1.add(detail1);

        final CalculationModel model0 = new CalculationModel(list0);
        final CalculationModel model1 = new CalculationModel(list0);
        final CalculationModel model2 = new CalculationModel(list1);

        assertEquals(new CalculationModel().hashCode(), new CalculationModel().hashCode());
        assertEquals(model0.hashCode(), model1.hashCode());
        assertNotSame(model0.hashCode(), model2.hashCode());
        assertEquals(model0.hashCode(), model0.hashCode());
    }

    @Test
    public void toStringMethod() {
        final CalculationModelDetailList list = new CalculationModelDetailList();
        final CalculationModelDetail detail0 = new CalculationModelDetail();
        list.add(detail0);
        detail0.setAbsolute(new Price(10));
        detail0.setPerCent(new BigDecimal("0.10"));

        final CalculationModelDetail detail1 = new CalculationModelDetail();
        list.add(detail1);

        final CalculationModel model = new CalculationModel(list);

        assertEquals(model.toString(), "CalculationModel{detailList=[" +
                "CalculationModelDetail{perCent=0.10, absolute=10, priceRange=null}, " +
                "CalculationModelDetail{perCent=null, absolute=null, priceRange=null}]}");
        assertNotNull(new CalculationModelDetailList().toString());
    }
}
