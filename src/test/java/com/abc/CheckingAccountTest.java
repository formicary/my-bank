package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class CheckingAccountTest {

    @Test
    public void dailyInterestEarned() {
        final double INTEREST_RATE = 0.001;
        final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
        final int BIG_DECIMAL_SCALE = 10;
        final int DAYS_IN_YEAR = 365;

        final BigDecimal DAILY_INTEREST_RATE = BigDecimal.valueOf(INTEREST_RATE).divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT);
        final BigDecimal BALANCE = BigDecimal.valueOf(599.99);

        final BigDecimal ANNUAL_INTEREST = BALANCE.multiply(BigDecimal.valueOf(INTEREST_RATE)).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        assertNotEquals("Test that annual interest is NOT calculated", ANNUAL_INTEREST, new CheckingAccount().dailyInterestEarned(BALANCE).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        final BigDecimal DAILY_INTEREST = BALANCE.multiply(DAILY_INTEREST_RATE).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        assertEquals("Test that daily interest IS calculated", DAILY_INTEREST, new CheckingAccount().dailyInterestEarned(BALANCE).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

    }
}
