package de.softconex.assessment.calcmodel.model.price;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Objects;

/**
 * {@link Price} range (min/max).
 * Null values minimum/maximum will be ignored (considered as "no limit").
 */
public class PriceRange {
    public static final String XML_PRICERANGE = "priceRange";
    private static final String XML_MINIMUM = "minimum";
    private static final String XML_MAXIMUM = "maximum";

    private final Price minimum;
    private final Price maximum;

    /**
     * Constructs a {@link PriceRange} from integer values.
     * Integer values converting to {@link Price} objects.
     */
    public PriceRange(int minimum, int maximum) {
        this.minimum = new Price(minimum);
        this.maximum = new Price(maximum);
    }

    /**
     * Constructs a {@link PriceRange} from {@link Price} objects.
     */
    public PriceRange(Price minimum, Price maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public final Price getMinimum() {
        return minimum;
    }

    public final Price getMaximum() {
        return maximum;
    }

    /**
     * Returns whether the passed price is inside "this" range.
     *
     * @param price (if null, a IllegalArgumentException will be thrown).
     */
    public boolean contains(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Tried to find price in list with price=null");
        }

        if (minimum != null && (minimum.getAmount().compareTo(price.getAmount()) > 0)) {
            return false;
        }

        if (maximum != null) {
            return maximum.getAmount().compareTo(price.getAmount()) >= 0;
        }

        return true;
    }

    /**
     * Converts this {@link PriceRange} to an XML element.
     * If minimum or maximum is null, the corresponding element is omitted.
     */
    public Element toXml() {
        Element out = DocumentHelper.createElement(XML_PRICERANGE);

        if (minimum != null) {
            out.add(minimum.toXml(XML_MINIMUM));
        }

        if (maximum != null) {
            out.add(maximum.toXml(XML_MAXIMUM));
        }

        return out;
    }

    /**
     * Parses a {@link PriceRange} from an XML element.
     * If child element is missing, it will be null.
     *
     * @param parent (if null, null will be returned).
     */
    public static PriceRange parse(Element parent) {
        if (parent == null) {
            return null;
        }
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

        return "[" +
                (minimum == null ? "noLimit" : minimum) +
                " - " +
                (maximum == null ? "noLimit" : maximum) +
                "]";
    }
}
