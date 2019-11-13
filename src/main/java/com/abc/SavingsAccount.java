package com.abc;

import java.math.BigDecimal;
import java.math.MathContext;

/** Subclass of Account representing a savings account. */
public class SavingsAccount extends Account {

    /** The annual interest rate applied to the lower balance tier. */
    private static final double LOWER_INTEREST_RATE = 0.001;
    /** The cut-off amount for the lower interest tier. */
    private static final double LOWER_INTEREST_AMOUNT = 1000;
    /** The annual interest rate applied to the higher balance tier. */
    private static final double HIGHER_INTEREST_RATE = 0.002;

    /**
     * Instantiates a new savings account. Sets parent \'accountType\' attribute to \' * Savings
     * Account\'.
     */
    public SavingsAccount() {
        super("Savings Account");
    }

    BigDecimal dailyInterestEarned(BigDecimal balance) {
        BigDecimal returnValue;

        if (balance.compareTo(BigDecimal.valueOf(LOWER_INTEREST_AMOUNT)) <= 0)
            returnValue =
                    balance.multiply(BigDecimal.valueOf(LOWER_INTEREST_RATE))
                            .divide(
                                    BigDecimal.valueOf(getDaysInYearForDailyInterestRate()),
                                    MathContext.DECIMAL128);
        else
            returnValue =
                    BigDecimal.valueOf(LOWER_INTEREST_AMOUNT)
                            .multiply(BigDecimal.valueOf(LOWER_INTEREST_RATE))
                            .divide(
                                    BigDecimal.valueOf(getDaysInYearForDailyInterestRate()),
                                    MathContext.DECIMAL128)
                            .add(
                                    (balance.subtract(BigDecimal.valueOf(LOWER_INTEREST_AMOUNT)))
                                            .multiply(
                                                    BigDecimal.valueOf(HIGHER_INTEREST_RATE)
                                                            .divide(
                                                                    BigDecimal.valueOf(
                                                                            getDaysInYearForDailyInterestRate()),
                                                                    MathContext.DECIMAL128)));

        return returnValue;
    }
}
