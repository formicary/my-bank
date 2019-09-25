package com.abc.accounts;

/**
 * @project MyBank
 */
public class Checking extends Account {

    public Checking(){
        super();
        interestRate = 0.001;
        accrueRate = interestRate/365;
    }

    @Override
    protected void compoundInterest() {
        balance += balance * interestRate;
    }

    @Override
    protected void accrueInterest() {
        interestRate += accrueRate;
    }

    @Override
    public String toString() {
        return "Checking Account";
    }
}
