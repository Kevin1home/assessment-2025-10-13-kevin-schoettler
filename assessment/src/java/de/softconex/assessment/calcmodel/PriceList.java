package de.softconex.assessment.calcmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A list of {@link Price} objects.
 */
public class PriceList extends ArrayList<Price> {
	private static final long serialVersionUID = 1L;
	private final static Comparator<Price> PRICE_COMPARATOR_ASCENDING = new Comparator<Price>() {
		public int compare(Price o1, Price o2) {
			return o1.getAmount().compareTo(o2.getAmount());
		}
	};

	/**
	 * Sorts this list in natural ascending order.
	 * 
	 */
	public void sortAscending() {
		Collections.sort(this, PRICE_COMPARATOR_ASCENDING);
	}

	private final static Comparator<Price> PRICE_COMPARATOR_DESCENDING = new Comparator<Price>() {
		public int compare(Price o1, Price o2) {
			return o2.getAmount().compareTo(o1.getAmount());
		}
	};

	/**
	 * Sorts this list in natural descending order.
	 * 
	 */
	public void sortDescending() {
		Collections.sort(this, PRICE_COMPARATOR_DESCENDING);
	}
}
