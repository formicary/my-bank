package com.abc;

public class SavingsAccount extends Account {

    public static final double bracketThreshold = 1000;
    public static final double interestRateLow = 0.001;
    public static final double interestRateHigh = 0.002;

    public SavingsAccount() {
        super();
        accountType = 1; // Savings Account
    }

    @Override
    public double calculateInterest(double diffInDays, double balance) {
        if (balance <= bracketThreshold)
            return (balance * Math.pow((1 + interestRateLow / 365), (diffInDays))) - balance;
        else {
            double temp = (bracketThreshold * Math.pow((1 + interestRateLow / 365), diffInDays)) - bracketThreshold;
            return temp + (balance - bracketThreshold) * interestRateHigh;
        }
    }

}