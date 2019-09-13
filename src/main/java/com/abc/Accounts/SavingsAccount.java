package com.abc.Accounts;

public class SavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Savings Accounts";

    public String getName() {
        return ACCOUNT_NAME;
    }

    public double interestEarned() {
        double amount = calculateBalance();
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount-1000) * 0.002;
    }
}
