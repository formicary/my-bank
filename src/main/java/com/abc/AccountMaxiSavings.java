package com.abc;

import com.util.BigDecimalProvider;

import java.math.BigDecimal;


public class AccountMaxiSavings extends Account {

    private final BigDecimal MAXI_SAVINGS_LOW_THRESHOLD_AMOUNT = BigDecimalProvider.format(1000);
    private final BigDecimal MAXI_SAVINGS_MID_THRESHOLD_AMOUNT = BigDecimalProvider.format(2000);
    private final BigDecimal MAXI_SAVINGS_RATE_LOW = BigDecimalProvider.getInterestRateFormatted(0.02);
    private final BigDecimal MAXI_SAVINGS_RATE_MID = BigDecimalProvider.getInterestRateFormatted(0.05);
    private final BigDecimal MAXI_SAVINGS_RATE_HIGH = BigDecimalProvider.getInterestRateFormatted(0.1);


    @Override
    public BigDecimal getInterestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal belowThreshold1;
        BigDecimal belowThreshold2;
        BigDecimal aboveThreshold;

        if (amount.compareTo(MAXI_SAVINGS_LOW_THRESHOLD_AMOUNT) <= 0) {
            return calculateInterest(amount, MAXI_SAVINGS_RATE_LOW);
        }

        if (amount.compareTo(MAXI_SAVINGS_MID_THRESHOLD_AMOUNT) <= 0) {
            belowThreshold1 = calculateInterest(MAXI_SAVINGS_LOW_THRESHOLD_AMOUNT, MAXI_SAVINGS_RATE_LOW);
            aboveThreshold = calculateInterest(amount.subtract(MAXI_SAVINGS_LOW_THRESHOLD_AMOUNT), MAXI_SAVINGS_RATE_MID);
            return belowThreshold1.add(aboveThreshold);
        }

        belowThreshold1 = calculateInterest(MAXI_SAVINGS_LOW_THRESHOLD_AMOUNT, MAXI_SAVINGS_RATE_LOW);
        belowThreshold2 = calculateInterest(
                MAXI_SAVINGS_MID_THRESHOLD_AMOUNT.subtract(MAXI_SAVINGS_LOW_THRESHOLD_AMOUNT),
                MAXI_SAVINGS_RATE_MID);
        aboveThreshold = calculateInterest(
                amount.subtract(MAXI_SAVINGS_MID_THRESHOLD_AMOUNT),
                MAXI_SAVINGS_RATE_HIGH);
        return belowThreshold1.add(belowThreshold2).add(aboveThreshold);

    }

    @Override
    protected String getAccountTypeLabel() {
        return "Maxi Savings Account";
    }
}
