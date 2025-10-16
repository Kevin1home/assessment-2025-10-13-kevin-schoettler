package de.softconex.assessment.calcmodel.model.calculation;

import de.softconex.assessment.calcmodel.model.price.Price;
import de.softconex.assessment.calcmodel.model.price.PriceRange;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * List of {@link CalculationModelDetail} objects.
 */
@SuppressWarnings("serial")
public class CalculationModelDetailList extends ArrayList<CalculationModelDetail> {
    public static final String XML_CALCULATIONMODELDETAILLIST = "calculationModelDetailList";

    /**
     * Finds the first {@link CalculationModelDetail} whose price range contains the given price.
     * Null elements are skipped.
     *
     * @param price (if null, a IllegalArgumentException will be thrown).
     * @return {@link CalculationModelDetail} or null
     */
    public CalculationModelDetail find(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Tried to find calculationModelDetail for price==null");
        }
        return this.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getPriceRange() == null || d.getPriceRange().contains(price))
                .findFirst().orElse(null);
    }

    /**
     * Sorts the list in ascending order by minimum {@link Price} in {@link PriceRange}.
     * Null elements are moved to the end of the list.
     */
    public void sortByMinimumAscending() {
        sort(Comparator.nullsLast(CalculationModelDetailList::compare));
    }

    /**
     * Compares two {@link CalculationModelDetail} objects by minimum {@link Price} in {@link PriceRange}.
     * <p>
     * - Null {@link PriceRange} is treated as greater than non-null {@link PriceRange}.
     * - Null minimum {@link Price} is treated as less than non-null minimums.
     */
    private static int compare(CalculationModelDetail detail1, CalculationModelDetail detail2) {

        PriceRange priceRange1 = detail1.getPriceRange();
        PriceRange priceRange2 = detail2.getPriceRange();

        if (priceRange1 == null || priceRange2 == null) {
            if (priceRange1 == null && priceRange2 == null) {
                return 0;
            } else if (priceRange1 == null) { // && priceRange2 != null
                return 1;
            }
            // priceRange1 != null && priceRange2 == null
            return -1;
        }

        Price minimum1 = priceRange1.getMinimum();
        Price minimum2 = priceRange2.getMinimum();

        if (minimum1 == null || minimum2 == null) {
            if (minimum1 == null && minimum2 == null) {
                return 0;
            } else if (minimum1 == null) { // && minimum2 != null
                return -1;
            }
            // minimum1 != null && minimum2 == null
            return 1;
        }

        return minimum1.getAmount().compareTo(minimum2.getAmount());
    }

    /**
     * Converts this {@link CalculationModelDetail} to an XML element.
     * Null elements are skipped.
     */
    public Element toXml() {
        Element element = DocumentHelper.createElement(XML_CALCULATIONMODELDETAILLIST);
        for (CalculationModelDetail detail : this) {
            if (detail != null) {
                element.add(detail.toXml());
            }
        }
        return element;
    }

    /**
     * Parses a {@link CalculationModelDetailList} from an XML element.
     * If parsing by child element returns empty, child element will be skipped.
     *
     * @param parent (if null, null will be returned).
     */
    public static CalculationModelDetailList parse(Element parent) {
        if (parent == null) {
            return null;
        }

        CalculationModelDetailList list = new CalculationModelDetailList();

        for (Iterator<?> it = parent.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();
            if (element.getName().equals(CalculationModelDetail.XML_CALCULATIONMODELDETAIL)) {
                CalculationModelDetail detail = CalculationModelDetail.parse(element);
                if (detail != null) {
                    list.add(detail);
                }
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return this.stream()
                .map(detail -> detail == null ? "null" : detail.toString())
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
