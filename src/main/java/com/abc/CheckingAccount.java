package com.abc;

import java.util.UUID;

public class CheckingAccount extends Account {
    public CheckingAccount(UUID accountID) {
        super(accountID);
    }

    public double interestEarned(double turnover) {
        double amount = super.sumTransactions();
        return amount * 0.001 * turnover;
    }
}
