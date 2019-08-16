package com.abc;

public class CheckingsAccount extends Account {
    public CheckingsAccount(int accountType) {
        super(accountType);
    }

    public double interestEarned() {
        return 0;
    }
}
