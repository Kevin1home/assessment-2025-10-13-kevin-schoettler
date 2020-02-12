package de.softconex.assessment.calcmodel;

import java.util.ArrayList;

/**
 * List of {@link CalculationModelDetail} objects.
 */
@SuppressWarnings("serial")
public class CalculationModelDetailList extends ArrayList<CalculationModelDetail> {
	public CalculationModelDetail find(Price price) {
		return this.stream()
				.filter((CalculationModelDetail d) -> d.getPriceRange() == null || d.getPriceRange().contains(price))
				.findFirst().orElse(null);
	}
}
