package de.softconex.assessment.calcmodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculationModelDetailTest extends Assertions {
	@Test
	public void calculateAbsoluteOnly() {
		final CalculationModelDetail detail = new CalculationModelDetail();
		detail.setAbsolute(new Price(5));
		detail.setPerCent(null);

		final Price in = new Price(1);

		final Price calculated = detail.calculate(in, true);

		assertEquals(new Price(6), calculated);
	}

	@Test
	public void calculateAbsoluteAndPerCentNull() {
		final CalculationModelDetail detail = new CalculationModelDetail();
		detail.setAbsolute(null);
		detail.setPerCent(null);

		final Price in = new Price(1);

		final Price calculated = detail.calculate(in, true);

		assertEquals(new Price(1), calculated);
	}

	@Test
	public void toStringMethod() {
		assertNotNull(new CalculationModelDetail().toString());
	}
}
