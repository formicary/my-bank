package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class SavingsAccountTest {

    @Test
    public void dailyInterestEarned() {
        final double LOWER_INTEREST_RATE = 0.001, HIGHER_INTEREST_RATE = 0.002;
        final double LOWER_INTEREST_AMOUNT = 1000;
        final int DAYS_IN_YEAR = 365;
        final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
        final int BIG_DECIMAL_SCALE = 10;
        final BigDecimal DAILY_LOWER_RATE =
                BigDecimal.valueOf(LOWER_INTEREST_RATE)
                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT);
        final BigDecimal AMOUNT_LESS_THAN_CUT_OFF = new BigDecimal(LOWER_INTEREST_AMOUNT / 2);

        final BigDecimal EXPECTED_INTEREST_EARNED_AMOUNT_BELOW_CUT_OFF =
                AMOUNT_LESS_THAN_CUT_OFF
                        .multiply(DAILY_LOWER_RATE)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        assertEquals(
                "Test that the lower interest rate is applied to amounts below the cut off",
                EXPECTED_INTEREST_EARNED_AMOUNT_BELOW_CUT_OFF,
                new SavingsAccount()
                        .dailyInterestEarned(AMOUNT_LESS_THAN_CUT_OFF)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        final BigDecimal EXPECTED_INTEREST_EARNED_LOWER_EXACTLY =
                BigDecimal.valueOf(LOWER_INTEREST_AMOUNT)
                        .multiply(DAILY_LOWER_RATE);
        assertEquals(
                "Test that the lower interest rate is applied to the cut off amount",
                EXPECTED_INTEREST_EARNED_LOWER_EXACTLY.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                new SavingsAccount()
                        .dailyInterestEarned(BigDecimal.valueOf(LOWER_INTEREST_AMOUNT))
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        final BigDecimal DAILY_HIGHER_RATE =
                BigDecimal.valueOf(HIGHER_INTEREST_RATE)
                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT);
        final BigDecimal AMOUNT_ABOVE_CUT_OFF =
                BigDecimal.valueOf(LOWER_INTEREST_AMOUNT).add(BigDecimal.valueOf(999));
        final BigDecimal EXPECTED_INTEREST_EARNED_AMOUNT_ABOVE_CUT_OFF =
                AMOUNT_ABOVE_CUT_OFF
                        .subtract(BigDecimal.valueOf(LOWER_INTEREST_AMOUNT))
                        .multiply(DAILY_HIGHER_RATE)
                        .add(EXPECTED_INTEREST_EARNED_LOWER_EXACTLY)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        assertEquals(
                "Test that higher interest rates are applied to amounts above the cut off only",
                EXPECTED_INTEREST_EARNED_AMOUNT_ABOVE_CUT_OFF,
                new SavingsAccount()
                        .dailyInterestEarned(AMOUNT_ABOVE_CUT_OFF)
                        .setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));
    }
}
