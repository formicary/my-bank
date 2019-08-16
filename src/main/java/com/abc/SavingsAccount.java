package com.abc;

public class SavingsAccount extends Account {
    public SavingsAccount() {
        super();
    }

    public double interestEarned() {
        if (accountValue <= 1000) return accountValue * 0.001;
        else return 1 + (accountValue - 1000) * 0.002;
    }

    public String getAccountType() {
        return "Savings Account";
    }
}
