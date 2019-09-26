package com.abc.accounts;

import java.time.LocalDateTime;

/**
 * @project MyBank
 */
public class Savings extends Account {

    private double secInterestRate;
    private double secAccrueRate;

    public Savings(){
        super();
        init();
    }

    public Savings(LocalDateTime date){
        super(date);
        init();
    }

    private void init(){
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

    public double getSecInterestRate() {
        return secInterestRate;
    }

    public void setSecInterestRate(double secInterestRate) {
        this.secInterestRate = secInterestRate;
    }

    public double getSecAccrueRate() {
        return secAccrueRate;
    }

    public void setSecAccrueRate(double secAccrueRate) {
        this.secAccrueRate = secAccrueRate;
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
}
