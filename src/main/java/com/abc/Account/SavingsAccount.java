package com.abc.Account;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.abc.Utilities.Enums.AccountType;

public class SavingsAccount extends Account {
    private final BigDecimal LOW_INTEREST_RATE = BigDecimal.valueOf(0.001);
    private final BigDecimal HIGH_INTEREST_RATE = BigDecimal.valueOf(0.002);

    private final BigDecimal NEW_INTEREST_RATE_THRESHOLD = BigDecimal.valueOf(1000.00);

    private final BigDecimal MAX_LOW_INTEREST = BigDecimal.ONE;
    
    public SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    @Override
    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();

        if (amount.compareTo(NEW_INTEREST_RATE_THRESHOLD) <= 0) {
            amount = amount.multiply(LOW_INTEREST_RATE);
        } else {
            amount = amount.subtract(NEW_INTEREST_RATE_THRESHOLD).multiply(HIGH_INTEREST_RATE).add(MAX_LOW_INTEREST);
        }

        return amount.setScale(2, RoundingMode.CEILING);
    };
}
