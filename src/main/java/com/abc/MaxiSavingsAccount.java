package com.abc;

import java.util.ArrayList;
import java.util.List;

public class MaxiSavingsAccount extends Account implements InterestByRules {
    private List<InterestRateWithValue> interestRules = new ArrayList<>();
    {
        interestRules.add(new InterestRateWithValue(0.02,1000.0));
        interestRules.add(new InterestRateWithValue(0.05,1000.0));
    }

    public MaxiSavingsAccount(){
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
        defaultInterestRate = 0.1;
    }

    public double interestEarned() {
        return calculateInterestWithRules(totalAmount, interestRules, defaultInterestRate, 0.0);
    }

    public String toString() {
        return "Maxi Savings Account\n";
    }
}
