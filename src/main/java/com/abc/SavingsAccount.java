package com.abc;

public class SavingsAccount extends Account {

    private final double interestForFirstThousand = 1;
    
    @Override
    protected double computeInterest(double amount) {
        if (amount <= 1000) {
            return Utils.annualInterestWithDailyCompound(amount, 0.001);
        }
        else {
            return interestForFirstThousand 
                    + Utils.annualInterestWithDailyCompound(amount - 1000, 0.002);
        }
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
    
}
