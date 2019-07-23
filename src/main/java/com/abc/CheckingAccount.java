package com.abc;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super();
    }

    @Override
    public double getInterestEarned() {

        double amount = sumTransactions();

        return amount * 0.001;
    }
}
