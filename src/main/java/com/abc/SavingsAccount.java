package com.abc;

public class SavingsAccount extends Account {
    public SavingsAccount() {
        super("Savings Account");
    }

    public double interestEarned() {
        if (accountValue <= 1000) return accountValue * 0.001;
        else return 1 + (accountValue - 1000) * 0.002;
    }
}
