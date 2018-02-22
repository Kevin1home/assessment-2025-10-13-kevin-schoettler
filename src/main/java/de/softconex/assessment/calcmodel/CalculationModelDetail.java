package de.softconex.assessment.calcmodel;

import java.math.BigDecimal;

/**
 * Detail for a {@link CalculationModel}.
 * 
 */
public class CalculationModelDetail {
	public CalculationModelDetail(BigDecimal perCent, Price absolute, PriceRange priceRange) {
		super();
		this.perCent = perCent;
		this.absolute = absolute;
		this.priceRange = priceRange;
	}

	public CalculationModelDetail() {
		super();
	}

	private BigDecimal perCent;

	/**
	 * PerCent (0.5 = 50%).
	 * 
	 * @return
	 */
	public final BigDecimal getPerCent() {
		return perCent;
	}

	public final void setPerCent(BigDecimal perCent) {
		this.perCent = perCent;
	}

	private Price absolute;

	public final Price getAbsolute() {
		return absolute;
	}

	public final void setAbsolute(Price absolute) {
		this.absolute = absolute;
	}

	private PriceRange priceRange;

	public final PriceRange getPriceRange() {
		return priceRange;
	}

	public final void setPriceRange(PriceRange priceRange) {
		this.priceRange = priceRange;
	}

	public final Price calculate(final Price price, final boolean firstPerCent) {
		// TODO - homework: support firstPerCent method argument
		// TODO - homework: support perCent attribute
		// TODO - homework: check if the price is inside the passed range, if
		// not, throw an IllegalArgumentException

		Price out = price;

		if (getAbsolute() != null) {
			out = price.add(getAbsolute());
		}

		return out;
	}

	@Override
	public String toString() {
		return "CalculationModelDetail: " + getAbsolute() + ", " + getPerCent() + "%" + "; priceRange: "
				+ getPriceRange();
	}

}
