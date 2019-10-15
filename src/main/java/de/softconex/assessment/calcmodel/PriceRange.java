package de.softconex.assessment.calcmodel;

import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * {@link Price} range (min/max).
 */
public class PriceRange {
	private static final String XML_MINIMUM = "minimum";

	private static final String XML_MAXIMUM = "maximum";

	private final Price minimum;

	private final Price maximum;

	public PriceRange(final int minimum, final int maximum) {
		super();

		this.minimum = new Price(minimum);
		this.maximum = new Price(maximum);
	}

	public PriceRange(final Price minimum, final Price maximum) {
		super();

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
	public boolean equals(Object obj) {
		// includes obj == null and obj is nont a PriceRange object
		if (false == (obj instanceof PriceRange)) {
			return false;
		}

		// now that we are sure that obj != null and obj is a PriceRange
		// object, do the cast and compare minimum and maxium
		final PriceRange that = (PriceRange) obj;

		if (false == Price.equals(this.getMinimum(), that.getMinimum())) {
			return false;
		}

		if (false == Price.equals(this.getMaximum(), that.getMaximum())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Price Range: " + getMinimum() + "-" + getMaximum();
	}

	/**
	 * Returns whether the passed price is inside "this" range. Null values
	 * minimum/maximum will be ignored (considered as "no limit").
	 * 
	 * @param price
	 * @return
	 */
	public boolean contains(final Price price) {
		if (getMinimum() != null) {
			if (getMinimum().getAmount().compareTo(price.getAmount()) > 0) {
				return false;
			}
		}

		if (getMaximum() != null) {
			if (getMaximum().getAmount().compareTo(price.getAmount()) < 0) {
				return false;
			}
		}

		return true;
	}

	public Element toXml() {
		final Element out = DocumentHelper.createElement("priceRange");

		if (getMinimum() != null) {
			out.add(getMinimum().toXml(XML_MINIMUM));
		}

		if (getMaximum() != null) {
			out.add(getMaximum().toXml(XML_MAXIMUM));
		}

		return out;
	}

	public static final PriceRange parse(final Element parent) {
		Price minimum = null;
		Price maximum = null;

		for (final Iterator<?> it = parent.elementIterator(); it.hasNext();) {
			final Element element = (Element) it.next();

			if (element.getName().equals(XML_MINIMUM)) {
				minimum = Price.parse(element);
			} else if (element.getName().equals(XML_MAXIMUM)) {
				maximum = Price.parse(element);
			}
		}

		return new PriceRange(minimum, maximum);
	}
}
