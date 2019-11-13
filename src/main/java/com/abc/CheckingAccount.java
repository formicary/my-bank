package com.abc;

import java.math.BigDecimal;
import java.math.MathContext;

/** Subclass of Account representing a checking account. */
public class CheckingAccount extends Account {

    /** The annual interest rate on a checking account. */
    private static final double INTEREST_RATE = 0.001;

    /**
     * Instantiates a new checking account. Sets parent \'accountType\' attribute to \'Checking
     * Account\'.
     */
    public CheckingAccount() {
        super("Checking Account");
    }

    BigDecimal dailyInterestEarned(BigDecimal balance) {
        return balance.multiply(BigDecimal.valueOf(INTEREST_RATE))
                .divide(
                        BigDecimal.valueOf(getDaysInYearForDailyInterestRate()),
                        MathContext.DECIMAL128);
    }
}
