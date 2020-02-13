package de.softconex.assessment.calcmodel;

import java.util.ArrayList;

/**
 * A list of {@link Price} objects.
 */
@SuppressWarnings("serial")
public class PriceList extends ArrayList<Price> {
	public void sortAscending() {
		sort(PriceList::compare);
	}

	public void sortDescending() {
		sort((Price p1, Price p2) -> compare(p2, p1));
	}

	private static int compare(Price p1, Price p2) {
		return p1.getAmount().compareTo(p2.getAmount());
	}
}
