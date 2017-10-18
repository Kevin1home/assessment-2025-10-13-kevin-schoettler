package de.softconex.assessment.calcmodel;

import java.util.ArrayList;

/**
 * List of CalculationModelDetail objects.
 */
public class CalculationModelDetailList extends ArrayList<CalculationModelDetail> {
	private static final long serialVersionUID = 1L;

	public CalculationModelDetail find(final Price price) {
		for (CalculationModelDetail detail : this) {
			if (detail.getPriceRange() == null) {
				return detail;
			}

			if (detail.getPriceRange().contains(price)) {
				return detail;
			}
		}

		return null;
	}
}
