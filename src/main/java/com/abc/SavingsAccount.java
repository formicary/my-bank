package com.abc;

import java.lang.Math;

public class SavingsAccount extends Account {
    
    public SavingsAccount() {
        super();
        accountName = "Savings Account";
    }

    @Override
    public double interestEarned() {
        if (balance <= 1000) return balance * 0.001;
        else return 1.0 + (balance - 1000) * 0.002;
    }
}