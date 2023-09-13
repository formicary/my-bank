package com.abc;

import java.math.BigDecimal;

/**
 * Represents a Savings Account.
 */
public class SavingsAccount extends Account {

    /**
     * Constructs a new Savings Account.
     * The interest rate requirements have been interpreted as follows:
     * 0.1% for total balance <= $1,000
     * 0.2% for total balance thereafter.
     */
    public SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    /**
     * Calculates and returns the interest rate for the Savings Account based on the
     * account balance.
     *
     * @param balance The current balance of the account.
     * @return The calculated interest rate.
     */
    @Override
    public BigDecimal getInterestRate(BigDecimal balance) {
        BigDecimal interestRate = (balance.compareTo(SAVINGS_THRESHOLD) <= 0) ?
                SAVINGS_LOW_INTEREST_RATE : SAVINGS_HIGH_INTEREST_RATE;
        return interestRate;
    }
}
