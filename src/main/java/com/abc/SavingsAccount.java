package com.abc;

public class SavingsAccount extends Account {

    @Override
    protected double computeInterest(double amount) {
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount-1000) * 0.002;
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
    
}
