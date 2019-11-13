package com.abc;

import java.math.BigDecimal;
import java.math.MathContext;

/** Subclass of Account representing a maxi savings account. */
public class MaxiSavingsAccount extends Account {

    /** The annual interest rate applied to the lowest balance tier. */
    private static final double LOWEST_INTEREST_RATE = 0.02;
    /** The cut-off amount for the lowest interest tier. */
    private static final double LOWEST_INTEREST_AMOUNT = 1000;

    /** The annual interest rate applied to the middle balance tier. */
    private static final double MIDDLE_INTEREST_RATE = 0.05;
    /** The cut-off amount for the middle interest tier. */
    private static final double MIDDLE_INTEREST_AMOUNT = 2000;

    /** The annual interest rate applied to the highest balance tier. */
    private static final double HIGHEST_INTEREST_RATE = 0.1;

    /**
     * Instantiates a new MaxiSavings account. Sets parent \'accountType\' attribute to \'Maxi
     * Savings Account\'.
     */
    public MaxiSavingsAccount() {
        super("Maxi Savings Account");
    }

    BigDecimal dailyInterestEarned(BigDecimal balance) {
        BigDecimal returnValue;

        if (balance.compareTo(BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT)) <= 0)
            returnValue =
                    balance.multiply(BigDecimal.valueOf(LOWEST_INTEREST_RATE))
                            .divide(
                                    BigDecimal.valueOf(getDaysInYearForDailyInterestRate()),
                                    MathContext.DECIMAL128);
        else if (balance.compareTo(BigDecimal.valueOf(MIDDLE_INTEREST_AMOUNT)) <= 0)
            returnValue =
                    BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT)
                            .multiply(BigDecimal.valueOf(LOWEST_INTEREST_RATE))
                            .divide(
                                    BigDecimal.valueOf(getDaysInYearForDailyInterestRate()),
                                    MathContext.DECIMAL128)
                            .add(
                                    (balance.subtract(BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT)))
                                            .multiply(
                                                    BigDecimal.valueOf(MIDDLE_INTEREST_RATE)
                                                            .divide(
                                                                    BigDecimal.valueOf(
                                                                            getDaysInYearForDailyInterestRate()),
                                                                    MathContext.DECIMAL128)));
        else
            returnValue =
                    BigDecimal.valueOf(LOWEST_INTEREST_AMOUNT)
                            .multiply(BigDecimal.valueOf(LOWEST_INTEREST_RATE))
                            .divide(
                                    BigDecimal.valueOf(getDaysInYearForDailyInterestRate()),
                                    MathContext.DECIMAL128)
                            .add(
                                    BigDecimal.valueOf(
                                                    MIDDLE_INTEREST_AMOUNT - LOWEST_INTEREST_AMOUNT)
                                            .multiply(BigDecimal.valueOf(MIDDLE_INTEREST_RATE))
                                            .divide(
                                                    BigDecimal.valueOf(
                                                            getDaysInYearForDailyInterestRate()),
                                                    MathContext.DECIMAL128))
                            .add(
                                    balance.subtract(BigDecimal.valueOf(MIDDLE_INTEREST_AMOUNT))
                                            .multiply(BigDecimal.valueOf(HIGHEST_INTEREST_RATE))
                                            .divide(
                                                    BigDecimal.valueOf(
                                                            getDaysInYearForDailyInterestRate()),
                                                    MathContext.DECIMAL128));
        return returnValue;
    }
}
