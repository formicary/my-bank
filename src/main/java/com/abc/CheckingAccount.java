package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account{
    private static final double RATE = 0.001;

    public CheckingAccount() {
        super("Checking Account");
    }

    @Override
    public BigDecimal interestEarned() {
        return getBalance().multiply(BigDecimal.valueOf(RATE));
    }
}
