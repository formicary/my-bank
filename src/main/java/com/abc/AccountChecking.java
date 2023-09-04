package com.abc;

import com.util.BigDecimalProvider;

import java.math.BigDecimal;

public class AccountChecking extends Account {
    private final BigDecimal CHECKING_RATE = BigDecimalProvider.getInterestRateFormatted(0.001);

    @Override
    public BigDecimal getInterestEarned() {
        BigDecimal amount = sumTransactions();
        return calculateInterest(amount, CHECKING_RATE);
    }

    @Override
    protected String getAccountTypeLabel() {
        return "Checking Account";
    }
}
