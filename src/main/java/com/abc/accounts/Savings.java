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
        intRate = 0.001;
        secInterestRate = 0.002;
        accrueRate = intRate /365;
        secAccrueRate = secInterestRate/365;
    }

    @Override
    protected void compoundInterest() {
        if (balance <= 1000) balance += balance * intRate;
        else balance += (1000 * intRate) + ((balance - 1000) * (secInterestRate));
    }

    @Override
    protected void accrueInterest() {
        intRate += accrueRate;
        secInterestRate += secAccrueRate;
    }

    public double getSecInterestRate() {
        return secInterestRate;
    }


    public double getSecAccrueRate() {
        return secAccrueRate;
    }


    @Override
    public String toString() {
        return "Savings Account";
    }
}
