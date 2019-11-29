package com.abc;

import java.math.BigDecimal;

public class MaxiSavingsAccount extends Account {
    // RATES ESTABLISHED PREVIOUSLY
    private static final double WITHDRAW_SMALLER_10 = 0.001;
    private static final double WITHDRAW_LARGER_10 = 0.05;


    public MaxiSavingsAccount() {
        super("Maxi-Savings Account");
    }

    public BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal interest = daysSinceLastWithdraw() >= 10
                ? balance.multiply(BigDecimal.valueOf(WITHDRAW_LARGER_10))
                : balance.multiply(BigDecimal.valueOf(WITHDRAW_SMALLER_10));

        return interest;
    }
}
