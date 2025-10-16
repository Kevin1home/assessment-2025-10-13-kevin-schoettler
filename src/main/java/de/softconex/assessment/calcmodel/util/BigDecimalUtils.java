package de.softconex.assessment.calcmodel.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    @SuppressWarnings({"NumberEquality", "BigDecimalEquals"})
    public static boolean safeEqualsIgnoreScale(BigDecimal value1, BigDecimal value2) {

        if (value1 == value2) {
            // includes both null
            return true;
        }

        if (value1 == null || value2 == null) {
            // one value null, other value not null
            return false;
        }

        int scale1 = value1.scale();
        int scale2 = value2.scale();

        if (scale1 == scale2) {
            return value1.equals(value2);
        }

        int maxScale = Math.max(scale1, scale2);

        return value1.setScale(maxScale).equals(value2.setScale(maxScale));
    }

    // ! Hash not always match "equals" cause of ignoring scale !
    public static int safeHashCodeIgnoreScale(BigDecimal value) {

        if (value == null) {
            return 0;
        }
        return value.setScale(2, RoundingMode.HALF_UP).hashCode();
    }
}
