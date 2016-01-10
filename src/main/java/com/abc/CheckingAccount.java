package com.abc;

public class CheckingAccount extends Account {

    @Override
    protected double computeInterest(double amount) {
      return Utils.annualInterestWithDailyCompound(amount, 0.001);
    }
    
    @Override
    public String toString() {
        return "Checking Account";
    }

}
