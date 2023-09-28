package com.abc.account;

import java.math.BigDecimal;

import com.abc.utilities.enums.AccountType;

/*
 * Savings Account - inherits from Account class
 */
public class SavingsAccount extends Account {
    private final BigDecimal LOW_ANNUAL_INTEREST_RATE = BigDecimal.valueOf(0.001);
    private final BigDecimal HIGH_ANNUAL_INTEREST_RATE = BigDecimal.valueOf(0.002);

    private final BigDecimal NEW_INTEREST_RATE_THRESHOLD = BigDecimal.valueOf(1000.00);

    /**
     * Intialises a new Savings Account instance object
     */
    public SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    /*
     * Calculates the interest earned for a Savings Account
     */
    @Override
    public BigDecimal getAnnualInterestRate() {
        BigDecimal amount = sumTransactions();
        BigDecimal annualInterestRate = (amount.compareTo(NEW_INTEREST_RATE_THRESHOLD) <= 0) ? LOW_ANNUAL_INTEREST_RATE : HIGH_ANNUAL_INTEREST_RATE;
        return annualInterestRate;
    };
}
