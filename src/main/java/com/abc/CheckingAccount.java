package com.abc;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super();
    }

    public double interestEarned() {
        return accountValue * 0.001;
    }

    public String getAccountType() {
        return "Checking Account";
    }
}
