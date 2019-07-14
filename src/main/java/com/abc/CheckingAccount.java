package com.abc;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super();
    }

    @Override
    public void addInterest() {
        double amount = currentBalance();

        if (amount <= 0) {
            return;
        }

        // 0.1% annual interest applied daily
        depositInterest(amount * (0.001 / 365));
    }
}
