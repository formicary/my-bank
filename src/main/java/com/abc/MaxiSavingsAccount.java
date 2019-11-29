package com.abc;

import java.math.BigDecimal;

public class MaxiSavingsAccount extends Account {
    // Maxi-savings account rates declared previously
    private static final double INITIAL_RATE = 0.02;
    private static final double SECOND_RATE = 0.05;
    private static final double THIRD_RATE = 0.1;
    // Maxi-Savings account thresholds to trigger different rates
    private static final int FIRST_THRESHOLD = 1000;
    private static final int SECOND_THRESHOLD = 2000;

    // NEW RATES AS PER NEW FEATURE - TESTING
    private static final double NEW_RATE1 = 0.001;
    private static final double NEW_RATE2 = 0.05;


    public MaxiSavingsAccount() {
        super("Maxi-Savings Account");
    }

    public BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal interest = daysSinceLastWithdraw() >= 10
                ? balance.multiply(BigDecimal.valueOf(NEW_RATE2))
                : balance.multiply(BigDecimal.valueOf(NEW_RATE1));

        return interest;
    }
}
