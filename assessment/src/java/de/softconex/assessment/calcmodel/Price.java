package de.softconex.assessment.calcmodel;

import java.math.BigDecimal;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * An immutable {@link Price} object.
 */
public final class Price {
	private final BigDecimal amount;

	/**
	 * Returns the amount; since a {@link BigDecimal} itself is immutable, no defensive
	 * copies will be made here.
	 * 
	 * @return
	 */
	public final BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Constructs a new {@link Price} instance.
	 * 
	 * @param amount
	 *            (if null, a NullPointerException will be thrown).
	 */
	public Price(final BigDecimal amount) {
		super();

		if (amount == null) {
			throw new NullPointerException(
					"Tried to construct a new Price objects with amount==null");
		}

		this.amount = amount;
	}

	/**
	 * Constructs a new {@link Price} instance.
	 * 
	 * @param amount
	 *            (if null, a NullPointerException will be thrown).
	 */
	public Price(final Integer amount) {
		super();

		if (amount == null) {
			throw new NullPointerException(
					"Tried to construct a new Price objects with amount==null");
		}

		this.amount = new BigDecimal(amount);
	}

	/**
	 * Constructs a new {@link Price} instance.
	 * 
	 * @param amount
	 *            (if null, a NullPointerException will be thrown).
	 */
	public Price(final String amount) {
		super();

		if (amount == null) {
			throw new NullPointerException(
					"Tried to construct a new Price objects with string amount==null");
		}

		this.amount = new BigDecimal(amount);
	}

	/**
	 * Adds the passed {@link Price} object to "this" object. A new instance of {@link Price}
	 * will be returned.
	 * 
	 * @param price
	 * @return
	 */
	public Price add(final Price price) {
		return new Price(getAmount().add(price.getAmount()));
	}

	/**
	 * Convert to XML element using the default element name ("price").
	 * 
	 * @return
	 */
	public Element toXml() {
		return toXml("price");
	}

	/**
	 * Convert to XML element using the passed elementName.
	 * 
	 * @param elementName
	 * @return
	 */
	public Element toXml(final String elementName) {
		final Element out = DocumentHelper.createElement(elementName);
		out.addAttribute("amount", getAmount().toString());
		return out;
	}

	/**
	 * Parse the passed element.
	 * 
	 * @param element
	 *            (if element==null, null will be returned).
	 * @return
	 */
	public final static Price parse(final Element element) {
		if (element == null) {
			return null;
		}

		final String amount = element.attributeValue("amount");
		return new Price(amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (false == (obj instanceof Price)) {
			return false;
		}

		final Price that = (Price) obj;
		return this.getAmount().equals(that.getAmount());
	}

	@Override
	public String toString() {
		return getAmount().toString();
	}

	@Override
	public int hashCode() {
		return getAmount().hashCode();
	}

	/**
	 * Returns whether the two {@link Price} instances are equal allowing null values. If both
	 * prices are null, they will be considered equal.
	 * 
	 * @param price1
	 * @param price2
	 * @return
	 */
	public final static boolean equals(final Price price1, final Price price2) {
		if (price1 == null && price2 == null) {
			return true;
		}

		if (price1 == null || price2 == null) {
			return false;
		}

		return price1.equals(price2);
	}
}
