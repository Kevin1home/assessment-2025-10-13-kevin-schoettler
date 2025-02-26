package de.softconex.assessment.calcmodel;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Objects;

/**
 * {@link Price} range (min/max).
 */
public class PriceRange {
    private static final String XML_MINIMUM = "minimum";

    private static final String XML_MAXIMUM = "maximum";

    private final Price minimum;

    private final Price maximum;

    public PriceRange(int minimum, int maximum) {
        this.minimum = new Price(minimum);
        this.maximum = new Price(maximum);
    }

    public PriceRange(Price minimum, Price maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public final Price getMaximum() {
        return maximum;
    }

    public final Price getMinimum() {
        return minimum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PriceRange that = (PriceRange) o;
        return Objects.equals(minimum, that.minimum) &&
                Objects.equals(maximum, that.maximum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, maximum);
    }

    @Override
    public String toString() {
        return "Price Range: " + getMinimum() + "-" + getMaximum();
    }

    /**
     * Returns whether the passed price is inside "this" range. Null values
     * minimum/maximum will be ignored (considered as "no limit").
     */
    public boolean contains(Price price) {
        if (getMinimum() != null && (getMinimum().getAmount().compareTo(price.getAmount()) > 0)) {
            return false;
        }

        if (getMaximum() != null) {
            return getMaximum().getAmount().compareTo(price.getAmount()) >= 0;
        }

        return true;
    }

    public Element toXml() {
        Element out = DocumentHelper.createElement("priceRange");

        if (getMinimum() != null) {
            out.add(getMinimum().toXml(XML_MINIMUM));
        }

        if (getMaximum() != null) {
            out.add(getMaximum().toXml(XML_MAXIMUM));
        }

        return out;
    }

    public static PriceRange parse(Element parent) {
        Price minimum = null;
        Price maximum = null;

        for (Iterator<?> it = parent.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();

            if (element.getName().equals(XML_MINIMUM)) {
                minimum = Price.parse(element);
            } else if (element.getName().equals(XML_MAXIMUM)) {
                maximum = Price.parse(element);
            }
        }

        return new PriceRange(minimum, maximum);
    }
}
