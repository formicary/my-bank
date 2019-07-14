package com.abc;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        super();
    }

    @Override
    public void addInterest() {
        double amount = currentBalance();
        if (amount <= 0) {
            return;
        }

        // Amount less than or equal to $1000, so annual interest is 0.1%, applied daily
        if (amount <= 1000) {
            depositInterest(amount * (0.001 / 365));
        } else {
            // Amount over $1000, so annual interest is 0.2%, applied daily
            depositInterest((1.0 / 365) + ((amount - 1000) * (0.002 / 365)));
        }
    }
}
