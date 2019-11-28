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

    public MaxiSavingsAccount() {
        super("Maxi-Savings Account");
    }

    public BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal interest;

        if(balance.compareTo(BigDecimal.valueOf(FIRST_THRESHOLD)) <= 0) {
            interest = balance.multiply(BigDecimal.valueOf(INITIAL_RATE));
        } else if(balance.compareTo(BigDecimal.valueOf(FIRST_THRESHOLD)) > 0
                && balance.compareTo(BigDecimal.valueOf(SECOND_THRESHOLD)) <= 0) {
            // '20' magic number represents interest earned with the INITIAL_RATE
            interest = balance.subtract(BigDecimal.valueOf(FIRST_THRESHOLD))
                    .multiply(BigDecimal.valueOf(SECOND_RATE))
                    .add(BigDecimal.valueOf(20));
        } else {
            // '70' magic number represents interest earned with the SECOND_RATE
            interest = balance.subtract(BigDecimal.valueOf(SECOND_THRESHOLD))
                    .multiply(BigDecimal.valueOf(THIRD_RATE))
                    .add(BigDecimal.valueOf(70));
        }

        return interest;
    }
}
