package com.abc.Accounts;

public class MaxiSavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Maxi-Savings Accounts";

    public String getName() {
        return ACCOUNT_NAME;
    }

    public double interestEarned() {
        double amount = calculateBalance();
        if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000)
            return 20 + (amount-1000) * 0.05;
        return 70 + (amount-2000) * 0.1;
    }
}
