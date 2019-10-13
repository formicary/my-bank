package com.abc;

import java.math.BigDecimal;

public class SavingAccount extends Account {
    public SavingAccount() {
        super("Savings Account");
    }

    @Override
    long interestEarned() {
        BigDecimal amount = BigDecimal.ZERO;
//        if (amount <= 1000)
//            return amount * 0.001;
//        else
//            return 1 + (amount - 1000) * 0.002;

        return 0;
    }
}
