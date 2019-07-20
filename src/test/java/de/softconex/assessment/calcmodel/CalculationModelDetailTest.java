package de.softconex.assessment.calcmodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link CalculationModelDetail} class.
 */
public class CalculationModelDetailTest extends Assertions {
	// perCent==null, absolute!=null
	@Test
	public void testCalculate1() {
		final CalculationModelDetail detail = new CalculationModelDetail();
		detail.setAbsolute(new Price(5));
		detail.setPerCent(null);

		final Price in = new Price(1);

		final Price calculated = detail.calculate(in, true);

		assertEquals(new Price(6), calculated);
	}

	// perCent==null; absolute==null
	@Test
	public void testCalculate2() {
		final CalculationModelDetail detail = new CalculationModelDetail();
		detail.setAbsolute(null);
		detail.setPerCent(null);

		final Price in = new Price(1);

		final Price calculated = detail.calculate(in, true);

		assertEquals(new Price(1), calculated);
	}

	/**
	 * mainly for line coverage and to make sure toString() works without
	 * 
	 * {@link NullPointerException}
	 */
	@Test
	public void testToString() {
		assertNotNull(new CalculationModelDetail().toString());
	}
}
