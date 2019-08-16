package com.abc;

public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        super();
        accountType = "Maxi-Savings Account\n";
    }

    public double interestEarned() {
        if (accountValue <= 1000) return accountValue * 0.02;
        if (accountValue <= 2000) return 20 + (accountValue - 1000) * 0.05;
        return 70 + (accountValue - 2000) * 0.1;
    }
}
