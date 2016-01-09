package com.abc;

public class CheckingAccount extends Account {

    @Override
    protected double computeInterest(double amount) {
      double interest = amount * Utils.annualInterestRateWithDailyCompound(0.001);
      return Utils.roundTo2Decimal(interest);
    }
    
    @Override
    public String toString() {
        return "Checking Account";
    }

}
