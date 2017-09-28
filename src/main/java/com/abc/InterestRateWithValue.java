package com.abc;

public class InterestRateWithValue {
    private double interestRate;
    private double decisionValue;

    public InterestRateWithValue(double interestRate, double decisionValue) {
        this.interestRate = interestRate;
        this.decisionValue = decisionValue;
    }
    public double getInterestRate() {
        return interestRate;
    }

    public double getDecisionValue() {
        return decisionValue;
    }
}
