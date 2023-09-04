package com.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalProvider {
    static final int DECIMAL_POINTS_AMOUNT = 2;
    static final int DECIMAL_POINTS_INTEREST_RATE = 3;
    static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    public static BigDecimal format(long value) {
        return configureAmount(BigDecimal.valueOf(value));
    }

    public static BigDecimal format(double value) {
        return configureAmount(BigDecimal.valueOf(value));
    }

    public static BigDecimal format(BigDecimal value) {
        return configureAmount(value);
    }

    public static BigDecimal getZero() {
        return format(0);
    }

    public static BigDecimal getInterestRateFormatted(double value) {
        return BigDecimal.valueOf(value);
    }

    private static BigDecimal configureAmount(BigDecimal value) {
        return value.setScale(DECIMAL_POINTS_AMOUNT, ROUNDING_MODE);
    }

    private static BigDecimal configureInterestRate(BigDecimal value) {
        return value.setScale(DECIMAL_POINTS_INTEREST_RATE, ROUNDING_MODE);
    }

}
