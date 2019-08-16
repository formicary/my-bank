package com.abc;

public class CheckingsAccount extends Account {
    public CheckingsAccount(int accountType) {
        super(accountType);
    }

    public double interestEarned() {
        return accountValue * 0.001;
    }
}
