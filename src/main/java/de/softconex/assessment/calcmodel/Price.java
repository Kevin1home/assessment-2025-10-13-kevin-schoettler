package de.softconex.assessment.calcmodel;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * An immutable {@link Price} object.
 * 
 * For simplicity reasons, the price class does not have a currency.
 * 
 * Scale can be ignored when checking for equality, e.g. "1.2" is the same as "1.20". 
 */
public final class Price {
	private final BigDecimal amount;

	/**
	 * Returns the amount; since a {@link BigDecimal} itself is immutable, no
	 * defensive copies will be made here.
	 */
	public final BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Constructs a new {@link Price} instance.
	 * 
	 * @param amount (if null, a NullPointerException will be thrown).
	 */
	public Price(BigDecimal amount) {

		if (amount == null) {
			throw new IllegalArgumentException("Tried to construct a new Price objects with amount==null");
		}

		this.amount = amount;
	}

	/**
	 * Constructs a new {@link Price} instance.
	 * 
	 * @param amount (if null, a NullPointerException will be thrown).
	 */
	public Price(Integer amount) {

		if (amount == null) {
			throw new IllegalArgumentException("Tried to construct a new Price objects with amount==null");
		}

		this.amount = new BigDecimal(amount);
	}

	/**
	 * Constructs a new {@link Price} instance.
	 * 
	 * @param amount (if null, a NullPointerException will be thrown).
	 */
	public Price(String amount) {

		if (amount == null) {
			throw new IllegalArgumentException("Tried to construct a new Price objects with string amount==null");
		}

		this.amount = new BigDecimal(amount);
	}

	/**
	 * Adds the passed {@link Price} object to "this" object. A new instance of
	 * {@link Price} will be returned.
	 */
	public Price add(Price price) {
		return new Price(getAmount().add(price.getAmount()));
	}

	/**
	 * Convert to XML element using the default element name ("price").
	 */
	public Element toXml() {
		return toXml("price");
	}

	/**
	 * Convert to XML element using the passed elementName.
	 */
	public Element toXml(String elementName) {
		Element out = DocumentHelper.createElement(elementName);
		out.addAttribute("amount", getAmount().toString());
		return out;
	}

	/**
	 * Parse the passed element.
	 * 
	 * @param element (if element==null, null will be returned).
	 */
	public static Price parse(Element element) {
		if (element == null) {
			return null;
		}

		String amount = element.attributeValue("amount");
		return new Price(amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Price)) {
			return false;
		}

		Price that = (Price) obj;

		return safeEqualsIgnoreScale(this.getAmount(), that.getAmount());
	}

	@Override
	public int hashCode() {
		return getAmount().hashCode();
	}

	@Override
	public String toString() {
		return getAmount().toString();
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

	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	@SuppressWarnings({ "NumberEquality", "BigDecimalEquals" })
	private static boolean safeEqualsIgnoreScale(BigDecimal value1, BigDecimal value2) {
		if (value1 == value2) {
			// includes both null
			return true;
		}

		if (value1 == null || value2 == null) {
			// one value null, other value not null
			return false;
		}

		int scale;
		int scale1 = value1.scale();
		int scale2 = value2.scale();

		if (scale1 == scale2) {
			return value1.equals(value2);
		}

		scale = Math.max(scale1, scale2);

		return value1.setScale(scale, ROUNDING_MODE).equals(value2.setScale(scale, ROUNDING_MODE));
	}
}
