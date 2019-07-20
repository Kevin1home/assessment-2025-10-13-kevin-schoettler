package de.softconex.assessment.calcmodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link CalculationModelDetailList} class.
 */
public class CalculationModelDetailListTest extends Assertions {
	// details with price ranges
	@Test
	public final void testFind1() {
		final CalculationModelDetailList list = new CalculationModelDetailList();

		final CalculationModelDetail detail0 = new CalculationModelDetail();
		list.add(detail0);
		detail0.setPriceRange(new PriceRange(0, 19));

		final CalculationModelDetail detail1 = new CalculationModelDetail();
		list.add(detail1);
		detail1.setPriceRange(new PriceRange(20, 29));

		assertEquals(detail0, list.find(new Price(19)));
		assertEquals(detail1, list.find(new Price(20)));
		assertNull(list.find(new Price(30)));
	}

	// one detail without price range
	@Test
	public final void testFind2() {
		final CalculationModelDetailList list = new CalculationModelDetailList();

		final CalculationModelDetail detail0 = new CalculationModelDetail();
		list.add(detail0);
		detail0.setPriceRange(new PriceRange(0, 19));

		final CalculationModelDetail detail1 = new CalculationModelDetail();
		list.add(detail1);
		detail1.setPriceRange(null);

		assertEquals(detail0, list.find(new Price(19)));
		assertEquals(detail1, list.find(new Price(20)));
		assertNotNull(list.find(new Price(30)));
	}
}