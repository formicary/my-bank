package com.abc;

import java.lang.Math;

public class MaxiSavingsAccount extends Account {
    
    public MaxiSavingsAccount() {
        super();
        accountName = "Maxi Savings Account";
    }

    @Override
    public double interestEarned() {
        // check transactions within last 10 days if any were withdrawals
        for (Transaction t: transactions) {
            if (t.daysCreated(DateProvider.getInstance().now()) <= 10) {
                if (t.amount < 0) return balance * 0.001;
            }
        }
        return balance * 0.05;
    }
}