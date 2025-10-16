package de.softconex.assessment.calcmodel.model.calculation;

import de.softconex.assessment.calcmodel.model.price.Price;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Objects;

/**
 * Represents a calculation model that contains a list of {@link CalculationModelDetail} objects.
 * Provides methods for calculation of sale price, serialization and deserialization of XML.
 */
public class CalculationModel {
    private static final String XML_CALCULATIONMODEL = "calculationModel";
    private final CalculationModelDetailList detailList;

    /**
     * Creates a new {@link CalculationModel} with the given detail list.
     *
     * @param detailList (if null, a IllegalArgumentException will be thrown).
     */
    public CalculationModel(CalculationModelDetailList detailList) {
        if (detailList == null) {
            throw new IllegalArgumentException("Tried to create a new calculationModel object with detailList==null");
        }
        this.detailList = detailList;
    }

    public CalculationModel() {
        this.detailList = new CalculationModelDetailList();
    }

    public CalculationModelDetailList getCalculationModelDetailList() {
        return detailList;
    }

    /**
     * Calculates a sale {@link Price} based on the given price and percentage mode.
     *
     * @param price (if null, a IllegalArgumentException will be thrown).
     * @param firstPerCent defines whether the percentage adjustment is applied before the absolute value
     * @return calculated sale {@link Price}
     */
    public Price calculate(Price price, boolean firstPerCent) {
        if (price == null) {
            throw new IllegalArgumentException("Tried to calculate new price with price==null");
        }
        CalculationModelDetail detail = detailList.find(price);
        if (detail == null) {
            return null;
        }
        return detail.calculate(price, firstPerCent);
    }

    /**
     * Calculates a sale {@link Price} based on the given price using the default percentage mode (true).
     *
     * @param price (if null, a IllegalArgumentException will be thrown).
     * @return calculated sale {@link Price} or null
     */
    public Price calculate(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Tried to calculate new price with price==null");
        }
        CalculationModelDetail detail = detailList.find(price);
        if (detail == null) {
            return null;
        }
        return detail.calculate(price, true);
    }

    /**
     * Converts this {@link CalculationModel} to an XML element.
     */
    public Element toXml() {
        Element out = DocumentHelper.createElement(XML_CALCULATIONMODEL);
        out.add(detailList.toXml());
        return out;
    }

    /**
     * Parses a {@link CalculationModel} from an XML element.
     * If parsing by child element returns empty, empty CalculationModelDetailList will be provided.
     *
     * @param parent (if null, null will be returned).
     */
    public static CalculationModel parse(Element parent) {
        if (parent == null) {
            return null;
        }
        CalculationModelDetailList detailList = new CalculationModelDetailList();

        for (Iterator<?> it = parent.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();

            if (element.getName().equals(CalculationModelDetailList.XML_CALCULATIONMODELDETAILLIST)) {
                CalculationModelDetailList parsedDetailList = CalculationModelDetailList.parse(element);
                if (parsedDetailList != null) {
                    detailList = parsedDetailList;
                    break;
                }
            }
        }
        return new CalculationModel(detailList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalculationModel that)) return false;
        return Objects.equals(detailList, that.detailList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detailList);
    }

    @Override
    public String toString() {
        return "CalculationModel{" +
                "detailList=" + detailList +
                '}';
    }
}
