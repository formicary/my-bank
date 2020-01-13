package com.abc;

import java.time.LocalDate;

public class SavingsAccount extends Account {
    public SavingsAccount() {
        super();
    }

    @Override
    double calculateInterest(double amount, LocalDate dateInQuestion) {
        if (amount <= 1000) {
            return (amount * 0.001)/365;
        }
        return ((1 + (amount-1000) * 0.002) /365);
    }

    @Override
    String getAccountString() {
        return "Savings Account\n";
    }
}
