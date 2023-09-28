package com.abc.account;

import java.math.BigDecimal;

import com.abc.utilities.enums.AccountType;

/**
 *  Checking Account - inherits from Account class
 */
public class CheckingAccount extends Account {
    private final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(0.001);

    /**
     * Initialises a new Checking Account instance object
     */
    public CheckingAccount() {
        super(AccountType.CHECKING);
    }

    /**
     * Calculates the interest earned for a Checking Account
     */
    @Override
    public BigDecimal getAnnualInterestRate() {
        return ANNUAL_INTEREST_RATE;
    }
}
