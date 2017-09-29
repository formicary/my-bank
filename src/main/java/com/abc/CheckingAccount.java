package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
        defaultInterestRate = 0.001;
    }

    public double interestEarned() {
        return interestEarned(totalAmount, null);
    }

    protected double interestEarned(double value, LocalDate date) {
        if (value < 0) {
            return 0;
        }
        return value * defaultInterestRate;
    }

    public String toString() {
        return "Checking Account\n";
    }
}
