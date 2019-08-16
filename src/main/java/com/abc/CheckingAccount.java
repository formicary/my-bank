package com.abc;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super();
        accountType = "Checking Account\n";
    }

    public double interestEarned() {
        return accountValue * 0.001;
    }
}
