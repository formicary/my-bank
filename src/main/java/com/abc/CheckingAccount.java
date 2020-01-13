package com.abc;

import java.time.LocalDate;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super();
    }

    @Override
    double calculateInterest(double amount, LocalDate dateInQuestion) {
        return (amount * 0.001)/365;
    }

    @Override
    String getAccountString() {
        return "Checking Account\n";
    }
}
