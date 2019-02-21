package com.abc;

import java.util.UUID;

public class SavingsAccount extends Account {
    public SavingsAccount(UUID accountID) {
        super(accountID);
    }

    public double interestEarned(double turnover) {
        double amount = super.sumTransactions();
        if (amount <= 1000)
            return amount * 0.001 * turnover;
        else
            return (1 + (amount - 1000) * 0.002) * turnover;
    }
}
