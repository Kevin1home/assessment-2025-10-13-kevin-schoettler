package de.softconex.assessment.calcmodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceListTest extends Assertions {
	@Test
	public void sort() {
		final PriceList list = new PriceList();
		list.add(new Price(5));
		list.add(new Price(3));
		list.add(new Price(7));

		list.sortAscending();
		assertEquals(new Price(3), list.get(0));
		assertEquals(new Price(5), list.get(1));
		assertEquals(new Price(7), list.get(2));

		list.sortDescending();
		assertEquals(new Price(7), list.get(0));
		assertEquals(new Price(5), list.get(1));
		assertEquals(new Price(3), list.get(2));
	}
}
