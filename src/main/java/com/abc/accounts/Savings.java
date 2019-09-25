package com.abc.accounts;

/**
 * @project MyBank
 */
public class Savings extends Account {

    private double secInterestRate;
    private double secAccrueRate;

    public Savings(){
        super();
        interestRate = 0.001;
        secInterestRate = 0.002;
        accrueRate = interestRate/365;
        secAccrueRate = secInterestRate/365;
    }

    @Override
    protected void compoundInterest() {
        if(balance <= 1000) balance += balance * interestRate;
        else balance = (1000 * interestRate) + ((balance - 1000) * (secInterestRate));
    }

    @Override
    protected void accrueInterest() {
        interestRate += accrueRate;
        secInterestRate += secAccrueRate;
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
}
