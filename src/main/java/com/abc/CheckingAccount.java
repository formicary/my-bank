package com.abc;

import java.util.ArrayList;

public class CheckingAccount extends Account {

    public double interestEarned() {
        return totalAmount * defaultInterestRate;
    }

    public CheckingAccount() {
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
        defaultInterestRate = 0.001;
    }

    public String toString() {
        return "Checking Account\n";
    }
}
