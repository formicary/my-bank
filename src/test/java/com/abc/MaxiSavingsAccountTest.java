package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class MaxiSavingsAccountTest {

    @Test
    public void dailyInterestEarned() {

        final double LOWEST_INTEREST_RATE = 0.02, LOWEST_INTEREST_AMOUNT = 1000;
        final double MIDDLE_INTEREST_RATE = 0.05, MIDDLE_INTEREST_AMOUNT = 2000;
        final double HIGHEST_INTEREST_RATE = 0.1;
        final int DAYS_IN_YEAR = 365;
        final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
        final int BIG_DECIMAL_SCALE = 10;

        final BigDecimal DAILY_LOWEST_RATE =
                BigDecimal.valueOf(LOWEST_INTEREST_RATE)
                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT);

        final BigDecimal AMOUNT_LESS_THAN_CUT_OFF = new BigDecimal(LOWEST_INTEREST_AMOUNT / 4);

        final BigDecimal EXPECTED_INTEREST_EARNED_AMOUNT_BELOW_CUT_OFF =
                AMOUNT_LESS_THAN_CUT_OFF
                        .multiply(DAILY_LOWEST_RATE)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        assertEquals(
                "Test that the lowest interest rate is applied to amounts below the lowest cut off",
                EXPECTED_INTEREST_EARNED_AMOUNT_BELOW_CUT_OFF,
                new MaxiSavingsAccount()
                        .dailyInterestEarned(AMOUNT_LESS_THAN_CUT_OFF)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        final BigDecimal EXPECTED_INTEREST_EARNED_LOWEST_EXACTLY =
                BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT)
                        .multiply(DAILY_LOWEST_RATE);
        assertEquals(
                "Test that the lowest interest rate is applied to the lowest cut off amount",
                EXPECTED_INTEREST_EARNED_LOWEST_EXACTLY.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                new MaxiSavingsAccount()
                        .dailyInterestEarned(BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT))
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        final BigDecimal DAILY_MIDDLE_RATE =
                BigDecimal.valueOf(MIDDLE_INTEREST_RATE)
                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT);
        final BigDecimal AMOUNT_ABOVE_LOWEST_CUT_OFF =
                BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT)
                        .add(BigDecimal.valueOf(123.45));
        final BigDecimal EXPECTED_INTEREST_EARNED_AMOUNT_ABOVE_LOWER_CUT_OFF =
                AMOUNT_ABOVE_LOWEST_CUT_OFF
                        .subtract(BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT))
                        .multiply(DAILY_MIDDLE_RATE)
                        .add(EXPECTED_INTEREST_EARNED_LOWEST_EXACTLY)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        assertEquals(
                "Test that middle interest rate is applied to amounts above the lowest cut off and below the middle cut off only",
                EXPECTED_INTEREST_EARNED_AMOUNT_ABOVE_LOWER_CUT_OFF,
                new MaxiSavingsAccount()
                        .dailyInterestEarned(AMOUNT_ABOVE_LOWEST_CUT_OFF)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        final BigDecimal EXPECTED_INTEREST_EARNED_MIDDLE_EXACTLY =
                BigDecimal.valueOf(MIDDLE_INTEREST_AMOUNT)
                        .subtract(BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT))
                        .multiply(DAILY_MIDDLE_RATE)
                        .add(EXPECTED_INTEREST_EARNED_LOWEST_EXACTLY);
        assertEquals(
                "Test that middle interest rate is applied to the exact middle amount only",
                EXPECTED_INTEREST_EARNED_MIDDLE_EXACTLY.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                new MaxiSavingsAccount()
                        .dailyInterestEarned(BigDecimal.valueOf(MIDDLE_INTEREST_AMOUNT))
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));


        final BigDecimal DAILY_HIGHEST_RATE =
                BigDecimal.valueOf(HIGHEST_INTEREST_RATE)
                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT);

        final BigDecimal AMOUNT_ABOVE_MIDDLE= BigDecimal.valueOf(MIDDLE_INTEREST_AMOUNT).add(BigDecimal.valueOf(1001));
        final BigDecimal EXPECTED_INTEREST_EARNED_ABOVE_MIDDLE =
                AMOUNT_ABOVE_MIDDLE
                        .subtract(BigDecimal.valueOf(MIDDLE_INTEREST_AMOUNT))
                        .multiply(DAILY_HIGHEST_RATE)
                        .add(EXPECTED_INTEREST_EARNED_MIDDLE_EXACTLY);
        assertEquals("Test that highest interest rate is applied to amounts above the middle cut off only", EXPECTED_INTEREST_EARNED_ABOVE_MIDDLE.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP), new MaxiSavingsAccount().dailyInterestEarned(AMOUNT_ABOVE_MIDDLE).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));
}
}
