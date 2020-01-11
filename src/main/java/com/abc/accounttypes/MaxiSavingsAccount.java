package com.abc.accounttypes;

public class MaxiSavingsAccount implements AccountType{
    
    private double interestRate1 = 0.02;
    private double moneyCap1 = 1000.0;

    private double interestRate2 = 0.05;
    private double moneyCap2 = 2000.0;

    private double interestRate3 = 0.1;


    public double interestEarned(double amount) {
        if(amount <= moneyCap1){
            return amount * interestRate1;
        }
        if (amount <= moneyCap2){
            return (amount - moneyCap1) * interestRate2 + moneyCap1 * interestRate1;
        }
        return (amount - moneyCap2) * interestRate3
            + (moneyCap2 - moneyCap1) * interestRate2 + moneyCap1 * interestRate1;

    }

    public String toString(){
        return "Maxi savings Account";
    } 
    
}