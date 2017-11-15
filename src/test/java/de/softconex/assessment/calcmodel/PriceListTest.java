package de.softconex.assessment.calcmodel;

import junit.framework.TestCase;

/**
 * Test cases for {@link PriceList} class.
 */
public class PriceListTest extends TestCase {
	public void testSort() {
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
