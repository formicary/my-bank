package com.abc;

import java.math.BigDecimal;

/**
 * Represents a checking account.
 */
public class CheckingAccount extends Account {

    /**
     * Constructs a new CheckingAccount instance.
     */
    public CheckingAccount() {
        super(AccountType.CHECKING);
    }

    /**
     * Gets the interest rate for a checking account.
     *
     * @param balance The balance of the account (not used for checking accounts).
     * @return The interest rate for a checking account.
     */
    @Override
    public BigDecimal getInterestRate(BigDecimal balance) {
        return CHECKING_INTEREST_RATE;
    }
}
