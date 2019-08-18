package com.abc;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super("Checking Account");
    }

    public double interestEarned() {
        return accountValue * 0.001;
    }
}
