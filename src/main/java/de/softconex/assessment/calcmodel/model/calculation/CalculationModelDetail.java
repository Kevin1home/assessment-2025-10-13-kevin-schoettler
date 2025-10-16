package de.softconex.assessment.calcmodel.model.calculation;

import de.softconex.assessment.calcmodel.model.price.Price;
import de.softconex.assessment.calcmodel.model.price.PriceRange;
import de.softconex.assessment.calcmodel.util.BigDecimalUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represents a model of calculation details that adjust initial {@link Price} with markup
 * considering a percentage, absolute value and {@link PriceRange}.
 */
public class CalculationModelDetail {
    public static final String XML_CALCULATIONMODELDETAIL = "calculationModelDetail";
    private static final String XML_PERCENT = "percent";
    private static final String XML_ABSOLUTE = "absolute";

    /**
     * PerCent (e.g. 0.5 = 50%).
     */
    private BigDecimal perCent;
    private Price absolute;
    private PriceRange priceRange;

    public CalculationModelDetail(BigDecimal perCent, Price absolute, PriceRange priceRange) {
        this.perCent = perCent;
        this.absolute = absolute;
        this.priceRange = priceRange;
    }

    public CalculationModelDetail() {
    }

    public final BigDecimal getPerCent() {
        return perCent;
    }

    public final void setPerCent(BigDecimal perCent) {
        this.perCent = perCent;
    }

    public final Price getAbsolute() {
        return absolute;
    }

    public final void setAbsolute(Price absolute) {
        this.absolute = absolute;
    }

    public final PriceRange getPriceRange() {
        return priceRange;
    }

    public final void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * Calculates a new {@link Price} based on the input price, applying perCent and/or absolute
     * adjustments according to the firstPerCent flag.
     *
     * @param price (if null, a IllegalArgumentException will be thrown).
     * @param firstPerCent (if true, percentage is applied before absolute; if false, absolute first).
     * @throws IllegalArgumentException (if price is outside the {@link PriceRange}).
     */
    public final Price calculate(Price price, boolean firstPerCent) {
        if (price == null) {
            throw new IllegalArgumentException("Tried to calculate new price with price=null");
        }

        if (priceRange != null) {
            if (!priceRange.contains(price)) {
                throw new IllegalArgumentException("Price not included in price range");
            }
        }

        if (absolute != null || perCent != null) {
            if (absolute == null) {
                // perCent exists && firstPerCent = true or false && absolute not exists
                // newPrice = price + (price * perCent)
                return price.add(price.multiplyBigDecimal(perCent));
            } else if (perCent == null) {
                // perCent not exists && absolute exists
                // newPrice = price + absolute
                return price.add(absolute);
            }
            if (firstPerCent) {
                // perCent exists && firstPerCent = true && absolute exists
                // newPrice = (price + (price * perCent)) + absolute
                return price.add(price.multiplyBigDecimal(perCent)).add(absolute);
            }
            // perCent exists && firstPerCent = false && absolute exists
            // newPrice = price + absolute + ((price + absolute) * perCent)
            return price.add(absolute).add(price.add(absolute).multiplyBigDecimal(perCent));
        }
        // perCent not exists && absolute not exists
        // newPrice = price
        return price;
    }

    /**
     * Converts this {@link CalculationModelDetail} to an XML element.
     * If perCent and/or absolute and/or priceRange is null, the corresponding element is omitted.
     */
    public Element toXml() {
        Element out = DocumentHelper.createElement(XML_CALCULATIONMODELDETAIL);

        if (perCent != null) {
            out.addAttribute(XML_PERCENT, perCent.toString());
        }

        if (absolute != null) {
            out.add(absolute.toXml(XML_ABSOLUTE));
        }

        if (priceRange != null) {
            out.add(priceRange.toXml());
        }

        return out;
    }

    /**
     * Parses a {@link CalculationModelDetail} from an XML element.
     * If child element is missing, it will be null.
     *
     * @param parent (if null, null will be returned).
     */
    public static CalculationModelDetail parse(Element parent) {
        if (parent == null) {
            return null;
        }
        BigDecimal perCent = null;
        Price absolute = null;
        PriceRange priceRange = null;

        String perCentString = parent.attributeValue(XML_PERCENT);
        if (perCentString != null && !perCentString.isBlank()) {
            perCent = new BigDecimal(perCentString);
        }

        for (Iterator<?> it = parent.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();

            if (element.getName().equals(XML_ABSOLUTE)) {
                absolute = Price.parse(element);
            } else if (element.getName().equals(PriceRange.XML_PRICERANGE)) {
                priceRange = PriceRange.parse(element);
            }
        }

        return new CalculationModelDetail(perCent, absolute, priceRange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CalculationModelDetail that = (CalculationModelDetail) o;
        return BigDecimalUtils.safeEqualsIgnoreScale(perCent, that.perCent) &&
                Objects.equals(absolute, that.absolute) &&
                Objects.equals(priceRange, that.priceRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                BigDecimalUtils.safeHashCodeIgnoreScale(perCent),
                absolute,
                priceRange);
    }

    @Override
    public String toString() {
        return "CalculationModelDetail{" +
                "perCent=" + perCent +
                ", absolute=" + absolute +
                ", priceRange=" + priceRange +
                '}';
    }
}
