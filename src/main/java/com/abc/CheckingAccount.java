package com.abc;

public class CheckingAccount extends Account {
    private double yearlyInterestRate;

    public CheckingAccount(){
        super();
        this.yearlyInterestRate = 0.001;
    }
    @Override
    public double interestEarnedDaily(){
        double amountToAccrueInterestOn = this.getAccountBalance(); //todo can this just be balance?
        return amountToAccrueInterestOn * dailyCompoundInterestRate(yearlyInterestRate);
    }
    @Override
    public String getAccountType() {
        return "Checking Account";
    }
}
