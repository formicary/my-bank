package com.abc;

public class CheckingAccount extends Account {

    public String getType() { return "Checking Account"; }

    public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }
}
