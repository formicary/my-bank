package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account implements InterestByRules {
    private List<InterestRateWithValue> interestRules = new ArrayList<>();
    {
        interestRules.add(new InterestRateWithValue(0.001,1000.0));
    }

    public SavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
        defaultInterestRate = 0.002;
    }

    public double interestEarned() {
        return interestEarned(totalAmount, null);
    }

    protected double interestEarned(double amount, LocalDate date) {
        return calculateInterestWithRules(amount, interestRules, defaultInterestRate, 0.0);
    }

    public String toString() {
        return "Savings Account\n";
    }

}
