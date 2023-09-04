package com.abc;

import com.util.BigDecimalProvider;

import java.math.BigDecimal;


public class AccountSavings extends Account {

    private final BigDecimal SAVINGS_THRESHOLD_AMOUNT = BigDecimalProvider.format(1000);
    private final BigDecimal SAVINGS_RATE_LOW = BigDecimalProvider.getInterestRateFormatted(0.001);
    private final BigDecimal SAVINGS_RATE_HIGH = BigDecimalProvider.getInterestRateFormatted(0.002);

    @Override
    public BigDecimal getInterestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal belowThreshold1;
        BigDecimal aboveThreshold;

        if (amount.compareTo(SAVINGS_THRESHOLD_AMOUNT) <= 0) {
            return calculateInterest(amount, SAVINGS_RATE_LOW);
        }

        belowThreshold1 = calculateInterest(SAVINGS_THRESHOLD_AMOUNT, SAVINGS_RATE_LOW);
        aboveThreshold = calculateInterest(amount.subtract(SAVINGS_THRESHOLD_AMOUNT), SAVINGS_RATE_HIGH);
        return belowThreshold1.add(aboveThreshold);
    }

    @Override
    protected String getAccountTypeLabel() {
        return "Savings Account";
    }
}
