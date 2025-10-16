package de.softconex.assessment.calcmodel.model.price;

import de.softconex.assessment.calcmodel.util.BigDecimalUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.math.BigDecimal;

/**
 * An immutable {@link Price} object.
 * <p>
 * For simplicity reasons, the price class does not have a currency.
 * <p>
 * Scale can be ignored when checking for equality, e.g. "1.2" is the same as "1.20".
 */
public final class Price {
    private static final String XML_PRICE = "price";
    private static final String XML_AMOUNT = "amount";
    private final BigDecimal amount;

    /**
     * Constructs a new {@link Price} instance.
     *
     * @param amount (if null, a IllegalArgumentException will be thrown).
     */
    public Price(BigDecimal amount) {

        if (amount == null) {
            throw new IllegalArgumentException("Tried to create a new price object with amount==null");
        }

        this.amount = amount;
    }

    /**
     * Constructs a new {@link Price} instance.
     * Converting Integer to BigDecimal
     *
     * @param amount (if null, a IllegalArgumentException will be thrown).
     */
    public Price(Integer amount) {

        if (amount == null) {
            throw new IllegalArgumentException("Tried to create a new price object with amount==null");
        }

        this.amount = new BigDecimal(amount);
    }

    /**
     * Constructs a new {@link Price} instance.
     * Converting String to BigDecimal
     *
     * @param amount (if null, blank or unacceptable format, a IllegalArgumentException will be thrown).
     */
    public Price(String amount) {

        if (amount == null || amount.isBlank()) {
            throw new IllegalArgumentException("Tried to create a new price object with string amount==null or empty");
        }

        try {
            this.amount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tried to create a new price object with unacceptable String format");
        }
    }

    /**
     * Returns the amount; since a {@link BigDecimal} itself is immutable, no
     * defensive copies will be made here.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Adds the passed {@link Price} object to "this" object. A new instance of
     * {@link Price} will be returned.
     *
     * @param price (if null, a IllegalArgumentException will be thrown).
     */
    public Price add(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Tried to add null to price");
        }
        return new Price(this.amount.add(price.amount));
    }

    /**
     * Multiply the price by a {@link BigDecimal}. Returns a new {@link Price} instance.
     *
     * @param bigDecimal (if null, a IllegalArgumentException will be thrown).
     */
    public Price multiplyBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            throw new IllegalArgumentException("Tried to multiply price by null");
        }
        return new Price(this.amount.multiply(bigDecimal));
    }

    /**
     * Convert to XML element using the default element name ("price").
     */
    public Element toXml() {
        return toXml(XML_PRICE);
    }

    /**
     * Convert to XML element using the passed elementName.
     *
     * @param elementName (if null or blank, a IllegalArgumentException will be thrown).
     */
    public Element toXml(String elementName) {
        if (elementName == null || elementName.isBlank()) {
            throw new IllegalArgumentException("Tried to convert string element==null or blank to XML");
        }

        Element out = DocumentHelper.createElement(elementName);
        out.addAttribute(XML_AMOUNT, amount.toString());
        return out;
    }

    /**
     * Parse the passed element.
     * If valid attribute by element not found, null will be returned.
     *
     * @param element (if null, null will be returned).
     */
    public static Price parse(Element element) {
        if (element == null) {
            return null;
        }

        String amountString = element.attributeValue(XML_AMOUNT);

        if (amountString == null || amountString.isBlank()) {
            return null;
        }
        return new Price(amountString);
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return customEqualsIgnoringScale(obj);
    }

    private boolean customEqualsIgnoringScale(Object obj) {
        if (!(obj instanceof Price that)) {
            return false;
        }

        return BigDecimalUtils.safeEqualsIgnoreScale(this.amount, that.amount);
    }

    /**
     * Returns whether the two {@link Price} instances are equal allowing null
     * values. If both prices are null, they will be considered equal.
     */
    public static boolean equals(Price price1, Price price2) {
        if (price1 == null && price2 == null) {
            return true;
        }

        if (price1 == null || price2 == null) {
            return false;
        }

        return price1.equals(price2);
    }

    @Override
    public int hashCode() {
        return customHashCodeIgnoringScale();
    }

    private int customHashCodeIgnoringScale() {
        return BigDecimalUtils.safeHashCodeIgnoreScale(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
