package com.abc;

public class SavingsAccount extends Account {

    @Override
    protected double computeInterest(double amount) {
        if (amount <= 1000) {
            double interest = Utils.annualInterestWithDailyCompound(amount, 0.001);
            return Utils.roundTo2Decimal(interest);
        }
        else {
            double interest = Utils.annualInterestWithDailyCompound(amount - 1000, 0.002);
            return 1 + Utils.roundTo2Decimal(interest);
        }
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
    
}
