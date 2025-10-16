package de.softconex.assessment.calcmodel.model.price;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * A list of {@link Price} objects.
 */
@SuppressWarnings("serial")
public class PriceList extends ArrayList<Price> {
    /**
     * Sorts the prices in ascending order.
     * Null elements are moved to the end of the list.
     */
    public void sortAscending() {
        sort(Comparator.nullsLast(PriceList::compare));
    }

    /**
     * Sorts the prices in descending order.
     * Null elements are moved to the end of the list.
     */
    public void sortDescending() {
        sort(Comparator.nullsLast((Price p1, Price p2) -> compare(p2, p1)));
    }

    /**
     * Compares two {@link Price} objects by their amount.
     */
    private static int compare(Price p1, Price p2) {
        return p1.getAmount().compareTo(p2.getAmount());
    }

    @Override
    public String toString() {
        return this.stream()
                .map(price -> price == null ? "null" : price.toString())
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
