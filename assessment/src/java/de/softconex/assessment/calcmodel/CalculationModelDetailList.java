package de.softconex.assessment.calcmodel;

import java.util.ArrayList;

/**
 * List of {@link CalculationModelDetail} objects.
 */
public class CalculationModelDetailList extends ArrayList<CalculationModelDetail> {
	private static final long serialVersionUID = 1L;

	public CalculationModelDetail find(final Price price) {
		return this.stream()
				.filter((CalculationModelDetail d) -> d.getPriceRange() == null || d.getPriceRange().contains(price))
				.findFirst().orElse(null);
	}
}
