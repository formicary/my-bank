package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    // Savings account rates declared previously
    private static final double INITIAL_RATE = 0.001;
    private static final double INCREASED_RATE = 0.002;
    // Savings account thresholds to trigger different rates
    private static final int THRESHOLD = 1000;

    public SavingsAccount() {
        super("Savings Account");
    }

    @Override
    public BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal interest;

        if(balance.compareTo(BigDecimal.valueOf(THRESHOLD)) <= 0) {
            interest = balance.multiply(BigDecimal.valueOf(INITIAL_RATE));
        } else {
            // '1' magic numbers represents interest earned with the INITIAL_RATE
            interest = balance.subtract(BigDecimal.valueOf(THRESHOLD))
                    .multiply(BigDecimal.valueOf(INCREASED_RATE))
                    .add(BigDecimal.valueOf(1));
        }

        return interest;
    }
}
