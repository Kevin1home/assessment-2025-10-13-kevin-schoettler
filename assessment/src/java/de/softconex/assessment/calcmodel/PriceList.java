package de.softconex.assessment.calcmodel;

import java.util.ArrayList;

/**
 * A list of {@link Price} objects.
 */
public class PriceList extends ArrayList<Price> {
	private static final long serialVersionUID = 1L;

	public void sortAscending() {
		sort((Price p1, Price p2) -> p1.getAmount().compareTo(p2.getAmount()));
	}

	public void sortDescending() {
		sort((Price p1, Price p2) -> p2.getAmount().compareTo(p1.getAmount()));
	}
}
